package com.mzuch.droidmovie.moviedetails.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MovieDetailsArgsData(
    val uid: Int,
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val score: String,
    val overview: String,
    val isFavorite: Boolean,
) : Parcelable