package com.mzuch.droidmovie.movies.usecase

import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val movieRepo: MovieDataSource
) {

    fun getMoviesFlow() = movieRepo.getMoviesFromPagingMediator()

    suspend fun markAsFavorite(movieUid: Int) {
        movieRepo.markFavorite(movieUid)
    }

    suspend fun unMarkAsFavorite(movieUid: Int) {
        movieRepo.unMarkFavorite(movieUid)
    }
}