package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import com.mzuch.droidmovie.data.movies.model.MovieDto
import com.mzuch.droidmovie.data.movies.model.MovieEntity

interface MovieLocalSource {
    suspend fun updateMovie(movie: MovieDto)
    suspend fun getMovie(uid: Int): MovieDto
    fun pagingSource() : PagingSource<Int, MovieEntity>
    suspend fun addFavorite(id: Int)
    suspend fun deleteFavorite(id: Int)
}