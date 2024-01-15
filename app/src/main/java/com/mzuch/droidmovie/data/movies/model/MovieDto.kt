package com.mzuch.droidmovie.data.movies.model

data class MovieDto(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val score: Double?,
    val overview: String,
    val popularity: Double,
    val localSortKey: Int,
    val isFavorite: Boolean,
)