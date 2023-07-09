package com.mzuch.droidmovie.data.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val score: Double?,
    val overview: String,
    val popularity: Double,
    val localSortKey: Int,
    @ColumnInfo(defaultValue = "0") val isFavorite: Boolean = false,
)
