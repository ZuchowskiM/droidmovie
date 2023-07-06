package com.mzuch.droidmovie.data.movies.repository.local

import com.mzuch.droidmovie.data.movies.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalSource {
    fun insertAll(movies: List<MovieEntity>)
    fun getAll(): Flow<List<MovieEntity>>
}