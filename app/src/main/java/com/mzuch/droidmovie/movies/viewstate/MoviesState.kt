package com.mzuch.droidmovie.movies.viewstate

import com.mzuch.droidmovie.data.movies.model.MovieEntity

sealed class MoviesState {
    object Idle : MoviesState()
    object Error : MoviesState()
    data class Success(val data: List<MovieEntity>) : MoviesState()
}
