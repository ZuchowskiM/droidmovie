package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.model.RepositoryResult
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MoviesData
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun loadMoviesData(): RepositoryResult<MoviesData>
    suspend fun getLiveMoviesData(): Flow<List<MovieEntity>>
    suspend fun markFavorite(movieUid: Int)
    suspend fun unMarkFavorite(movieUid: Int)
}