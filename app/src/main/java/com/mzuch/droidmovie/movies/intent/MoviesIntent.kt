package com.mzuch.droidmovie.movies.intent

sealed class MoviesIntent {
    object FetchData : MoviesIntent()
}
