package com.mzuch.droidmovie.moviedetails.view

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MovieDetailsArgsData(
    val posterPath: String,
    val title: String,
    val releaseDate: String,
    val score: String,
    val overview: String,
) : Parcelable