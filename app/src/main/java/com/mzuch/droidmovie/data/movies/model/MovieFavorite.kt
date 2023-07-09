package com.mzuch.droidmovie.data.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieFavorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(defaultValue = "1") val isFavorite: Boolean = true,
)
