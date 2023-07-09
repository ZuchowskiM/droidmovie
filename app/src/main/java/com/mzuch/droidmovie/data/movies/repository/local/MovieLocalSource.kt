package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import com.mzuch.droidmovie.data.movies.model.MovieEntity

interface MovieLocalSource {
    suspend fun updateMovie(movie: MovieEntity)
    suspend fun getMovie(uid: Int): MovieEntity
    fun pagingSource() : PagingSource<Int, MovieEntity>
    suspend fun addFavorite(id: Int)
    suspend fun deleteFavorite(id: Int)
}