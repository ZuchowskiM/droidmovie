package com.mzuch.droidmovie.data.movies.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val uid: String,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val score: Double?,
    val overview: String,
    val isFavorite: Boolean = false
)
