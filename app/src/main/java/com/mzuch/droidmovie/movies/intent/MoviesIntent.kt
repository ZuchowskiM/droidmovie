package com.mzuch.droidmovie.movies.intent

sealed class MoviesIntent {
    object RefreshError : MoviesIntent()
    class MarkAsFavorite(val movieUid: Int) : MoviesIntent()
    class UnMarkAsFavorite(val movieUid: Int) : MoviesIntent()
}
