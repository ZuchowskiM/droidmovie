package com.mzuch.droidmovie.data.movies.repository

import androidx.paging.PagingData
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {
    suspend fun markFavorite(movieUid: Int)
    suspend fun unMarkFavorite(movieUid: Int)
    fun getMoviesFromPagingMediator(): Flow<PagingData<MovieEntity>>
}