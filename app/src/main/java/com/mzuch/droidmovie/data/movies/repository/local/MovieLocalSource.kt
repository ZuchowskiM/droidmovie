package com.mzuch.droidmovie.data.movies.repository.local

import com.mzuch.droidmovie.data.movies.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalSource {
    suspend fun insertAll(movies: List<MovieEntity>)
    suspend fun getAll(): Flow<List<MovieEntity>>
    suspend fun deleteAll()
}