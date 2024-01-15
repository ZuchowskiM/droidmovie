package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.extension.toDto
import com.mzuch.droidmovie.data.movies.extension.toEntity
import com.mzuch.droidmovie.data.movies.model.MovieDto
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieFavorite

class MovieLocal(private val db: AppDatabase) : MovieLocalSource {

    override suspend fun updateMovie(movie: MovieDto) {
        return db.movieDao().update(movie.toEntity())
    }

    override suspend fun getMovie(uid: Int): MovieDto {
        return db.movieDao().getMovie(uid).toDto()
    }

    override fun pagingSource(): PagingSource<Int, MovieEntity> {
        return db.movieDao().pagingSource()
    }

    override suspend fun addFavorite(id: Int) {
        return db.movieFavoriteDao().insert(MovieFavorite(id))
    }

    override suspend fun deleteFavorite(id: Int) {
        return db.movieFavoriteDao().delete(id)
    }
}