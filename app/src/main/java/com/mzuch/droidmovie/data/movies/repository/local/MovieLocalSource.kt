package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieFavorite
import kotlinx.coroutines.flow.Flow

interface MovieLocalSource {
    suspend fun insertAll(movies: List<MovieEntity>)
    suspend fun getAllAsFlow(): Flow<List<MovieEntity>>
    suspend fun deleteAll()
    suspend fun updateMovie(movie: MovieEntity)
    suspend fun getMovie(uid: Int): MovieEntity
    suspend fun getAllFavorites(): List<MovieEntity>
    suspend fun updateFavoritesAll(movies: List<MovieFavorite>)
    suspend fun pagingSource() : PagingSource<Int, MovieEntity>
    suspend fun addFavorite(id: Int)
    suspend fun deleteFavorite(id: Int)
}