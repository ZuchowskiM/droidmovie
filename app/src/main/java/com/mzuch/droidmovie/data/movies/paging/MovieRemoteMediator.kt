package com.mzuch.droidmovie.data.movies.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.network.MovieApi
import com.mzuch.droidmovie.network.NetworkResponse

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieApi: MovieApi,
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
            LoadType.REFRESH -> MoviePagingConfig.INITIAL_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = false)
            LoadType.APPEND -> getLastRemoteKey(state)?.nextKey
                ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
        }
        when (val response = movieApi.getMoviesData(pageKey)) {
            is NetworkResponse.Success -> {
                val isEndOfList = response.body.results.isEmpty()
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.movieDao().deleteAll()
                        db.movieRemoteKeyDao().deleteAll()
                    }
                    val prevKey =
                        if (pageKey == MoviePagingConfig.INITIAL_PAGE) null else pageKey - 1
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
                            localSortKey = constructSortKey(pageKey, index)
                        )
                    }
                    val favoriteData = db.movieFavoriteDao().getAllFavorite()
                    db.movieRemoteKeyDao().insertAll(keys)
                    db.movieDao().insertAll(movieModels)
                    db.movieDao().updateFavoritesAll(favoriteData)
                }

                return MediatorResult.Success(endOfPaginationReached = isEndOfList)
            }

            else -> return MediatorResult.Error(Exception())
        }
    }

    // TODO make later sortKey that have some dignity
    private fun constructSortKey(pageKey: Int, index: Int) = pageKey * 1000 + (index + 1)

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        val key = state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie ->
                return@let db.movieRemoteKeyDao().remoteKeyMovieId(movie.id)
            }
        return key
    }
}