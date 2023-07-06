package com.mzuch.droidmovie.data.movies.repository.local

import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieLocal(private val db: AppDatabase) : MovieLocalSource {
    override fun insertAll(movies: List<MovieEntity>) {
        return db.movieDao().insertAll(*movies.toTypedArray())
    }

    override fun getAll(): Flow<List<MovieEntity>> {
        return db.movieDao().getAll()
    }
}