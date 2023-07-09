package com.mzuch.droidmovie.data.movies.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.paging.MovieRemoteMediator
import com.mzuch.droidmovie.data.movies.repository.local.MovieLocalSource
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemoteSource
import kotlinx.coroutines.flow.Flow

class MovieRepo(
    private val remote: MovieRemoteSource,
    private val local: MovieLocalSource,
    private val appDatabase: AppDatabase,
) : MovieDataSource {


    override suspend fun markFavorite(movieUid: Int) {
        val movieEntity = local.getMovie(movieUid)
        val updatedMovie = movieEntity.copy(
            isFavorite = true
        )
        local.updateMovie(updatedMovie)
        local.addFavorite(movieUid)
    }

    override suspend fun unMarkFavorite(movieUid: Int) {
        val movieEntity = local.getMovie(movieUid)
        val updatedMovie = movieEntity.copy(
            isFavorite = false
        )
        local.updateMovie(updatedMovie)
        local.deleteFavorite(movieUid)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesFromPagingMediator(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 40,
                prefetchDistance = 40,
            ),
            remoteMediator = MovieRemoteMediator(
                remote,
                appDatabase,
            ),
            pagingSourceFactory = { appDatabase.movieDao().pagingSource() }
        ).flow

    }
}