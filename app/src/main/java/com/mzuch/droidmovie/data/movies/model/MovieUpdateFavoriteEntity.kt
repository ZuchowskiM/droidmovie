package com.mzuch.droidmovie.data.movies.model

import androidx.room.Entity

@Entity
data class MovieUpdateFavoriteEntity(
    val id: Int,
    val isFavorite: Boolean
)