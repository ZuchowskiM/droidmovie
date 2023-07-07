package com.mzuch.droidmovie.data.movies.repository.local

import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateEntity
import kotlinx.coroutines.flow.Flow

class MovieLocal(private val db: AppDatabase) : MovieLocalSource {
    override suspend fun insertAll(movies: List<MovieUpdateEntity>) {
        return db.movieDao().insertAll(*movies.toTypedArray())
    }

    override suspend fun getAll(): Flow<List<MovieEntity>> {
        return db.movieDao().getAll()
    }

    override suspend fun deleteAll() {
        return db.movieDao().deleteAll()
    }

    override suspend fun updateMovie(movie: MovieEntity) {
        return db.movieDao().update(movie)
    }

    override suspend fun getMovie(uid: Int): MovieEntity {
        return db.movieDao().getMovie(uid)
    }
}