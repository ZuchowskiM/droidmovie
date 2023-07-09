package com.mzuch.droidmovie.data.movies.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateFavoriteEntity
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemoteSource
import com.mzuch.droidmovie.network.NetworkResponse

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieRemote: MovieRemoteSource,
    private val db: AppDatabase,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = false)
            LoadType.APPEND -> getLastRemoteKey(state)?.nextKey
                ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
        }
        when (val response = movieRemote.getMoviesData(pageKey)) {
            is NetworkResponse.Success -> {
                val isEndOfList = response.body.results.isEmpty()
                db.withTransaction {
                    var favorites: List<MovieUpdateFavoriteEntity>? = null
                    if (loadType == LoadType.REFRESH) {
                        favorites = db.movieDao().getAllFavorites().map {
                            MovieUpdateFavoriteEntity(
                                it.id,
                                it.isFavorite
                            )
                        }
                        db.movieDao().deleteAll()
                        db.movieRemoteKeyDao().deleteAll()
                    }
                    val prevKey = if (pageKey == 1) null else pageKey - 1
                    val nextKey = if (isEndOfList) null else pageKey + 1
                    val keys = response.body.results.map {
                        MovieRemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    val movieModels = response.body.results.mapIndexed { index, result ->
                        MovieEntity(
                            result.id,
                            result.title.orEmpty(),
                            result.posterPath,
                            result.releaseDate.orEmpty(),
                            result.voteAverage,
                            result.overview.orEmpty(),
                            popularity = result.popularity ?: 0.0,
                            localSortKey = pageKey * 1000 + (index + 1)
                        )
                    }
                    db.movieRemoteKeyDao().insertAll(keys)
                    db.movieDao().insertAll(movieModels)
                    favorites?.let { db.movieDao().updateFavoritesAll(it) }
                }

                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }

            else -> return MediatorResult.Error(Exception())
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        val key = state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie ->
                return@let db.movieRemoteKeyDao().remoteKeyMovieId(movie.id)
            }
        return key
    }
}