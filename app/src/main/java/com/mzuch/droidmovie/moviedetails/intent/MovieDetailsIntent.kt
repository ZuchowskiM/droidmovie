package com.mzuch.droidmovie.moviedetails.intent

sealed class MovieDetailsIntent {
    class MarkAsFavorite(val movieUid: Int) : MovieDetailsIntent()
    class UnMarkAsFavorite(val movieUid: Int) : MovieDetailsIntent()
}