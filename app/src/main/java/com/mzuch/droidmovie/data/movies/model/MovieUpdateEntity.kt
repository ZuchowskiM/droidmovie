package com.mzuch.droidmovie.data.movies.model

import androidx.room.Entity

@Entity
data class MovieUpdateEntity(
    val uid: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val score: Double?,
    val overview: String,
)