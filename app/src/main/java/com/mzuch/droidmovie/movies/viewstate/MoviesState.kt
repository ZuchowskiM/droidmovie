package com.mzuch.droidmovie.movies.viewstate

sealed class MoviesState {
    object Idle : MoviesState()
    object LoadError : MoviesState()
}
