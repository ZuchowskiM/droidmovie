package com.mzuch.droidmovie.data.movies.extension

import com.mzuch.droidmovie.data.movies.model.MovieDto
import com.mzuch.droidmovie.data.movies.model.MovieEntity

fun MovieEntity.toDto() = MovieDto(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    score = score,
    overview = overview,
    popularity = popularity,
    localSortKey = localSortKey,
    isFavorite = isFavorite
)

fun MovieDto.toEntity() = MovieEntity(
    id = id,
    title = title,
    posterPath = posterPath,
    releaseDate = releaseDate,
    score = score,
    overview = overview,
    popularity = popularity,
    localSortKey = localSortKey,
    isFavorite = isFavorite
)