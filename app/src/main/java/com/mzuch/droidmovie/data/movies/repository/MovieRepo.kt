package com.mzuch.droidmovie.data.movies.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mzuch.droidmovie.data.movies.extension.toDto
import com.mzuch.droidmovie.data.movies.model.MovieDto
import com.mzuch.droidmovie.data.movies.paging.MoviePagingConfig
import com.mzuch.droidmovie.data.movies.paging.MovieRemoteMediator
import com.mzuch.droidmovie.data.movies.repository.local.MovieLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepo(
    private val local: MovieLocalSource,
    private val movieRemoteMediator: MovieRemoteMediator,
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
    override fun getMoviesFromPagingMediator(): Flow<PagingData<MovieDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = MoviePagingConfig.PAGE_SIZE,
                initialLoadSize = MoviePagingConfig.PAGE_SIZE * 2,
                prefetchDistance = MoviePagingConfig.PAGE_SIZE * 2,
            ),
            remoteMediator = movieRemoteMediator,
            pagingSourceFactory = { local.pagingSource() }
        ).flow.map { pagingData -> pagingData.map { it.toDto() } }
    }
}