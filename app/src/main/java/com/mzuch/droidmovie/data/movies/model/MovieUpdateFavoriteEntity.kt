package com.mzuch.droidmovie.data.movies.model

import androidx.room.Entity

@Entity
data class MovieUpdateFavoriteEntity(
    val uid: Int,
    val isFavorite: Boolean
)