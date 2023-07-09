package com.mzuch.droidmovie.movies.viewstate

sealed class MoviesState {
    object Idle : MoviesState()
    data class Error(val msg: String) : MoviesState()
}
