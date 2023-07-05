package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.network.GenericResponse

interface MovieDataSource {
    suspend fun getMoviesData(): GenericResponse<MoviesData>
}