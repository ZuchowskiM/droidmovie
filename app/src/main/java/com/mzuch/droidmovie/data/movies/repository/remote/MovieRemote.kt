package com.mzuch.droidmovie.data.movies.repository.remote

import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.network.GenericResponse
import com.mzuch.droidmovie.network.MovieApi

class MovieRemote(private val apiService: MovieApi) : MovieRemoteSource {
    override suspend fun getMoviesData(): GenericResponse<MoviesData> = apiService.getMoviesData()
}