package com.mzuch.droidmovie.movies.viewstate

import com.mzuch.droidmovie.data.movies.model.Results

sealed class MoviesState {
    object Idle : MoviesState()
    object Loading : MoviesState()
    object Error : MoviesState()
    data class Success(val data: List<Results>) : MoviesState()
}
