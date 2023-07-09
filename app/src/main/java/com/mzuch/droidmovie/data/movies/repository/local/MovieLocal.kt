package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieFavorite
import kotlinx.coroutines.flow.Flow

class MovieLocal(private val db: AppDatabase) : MovieLocalSource {
    override suspend fun insertAll(movies: List<MovieEntity>) {
        return db.movieDao().insertAll(movies)
    }

    override suspend fun getAllAsFlow(): Flow<List<MovieEntity>> {
        return db.movieDao().getAllAsFlow()
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

    override suspend fun getAllFavorites(): List<MovieEntity> {
        return db.movieDao().getAllFavorites()
    }

    override suspend fun updateFavoritesAll(movies: List<MovieFavorite>) {
        return db.movieDao().updateFavoritesAll(movies)
    }

    override suspend fun pagingSource(): PagingSource<Int, MovieEntity> {
        return db.movieDao().pagingSource()
    }

    override suspend fun addFavorite(id: Int) {
        return db.movieFavoriteDao().insert(MovieFavorite(id))
    }

    override suspend fun deleteFavorite(id: Int) {
        return db.movieFavoriteDao().delete(id)
    }
}