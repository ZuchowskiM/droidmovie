package com.mzuch.droidmovie.data.movies.repository.remote

import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.network.GenericResponse

interface MovieRemoteSource {
    suspend fun getMoviesData(): GenericResponse<MoviesData>
}