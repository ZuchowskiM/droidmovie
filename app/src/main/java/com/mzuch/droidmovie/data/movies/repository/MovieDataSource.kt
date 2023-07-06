package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.model.RepositoryResult
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.network.GenericResponse
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun loadMoviesData(): RepositoryResult<MoviesData>
    suspend fun getLiveMoviesData(): Flow<List<MovieEntity>>
}