package com.mzuch.droidmovie.data.movies.repository.local

import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalSource {
    suspend fun insertAll(movies: List<MovieUpdateEntity>)
    suspend fun getAll(): Flow<List<MovieEntity>>
    suspend fun deleteAll()
    suspend fun updateMovie(movie: MovieEntity)
    suspend fun getMovie(uid: Int): MovieEntity
}