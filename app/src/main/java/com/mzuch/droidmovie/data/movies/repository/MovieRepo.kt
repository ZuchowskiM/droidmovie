package com.mzuch.droidmovie.data.movies.repository

import com.mzuch.droidmovie.data.movies.model.MoviesData
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemoteSource
import com.mzuch.droidmovie.network.GenericResponse

class MovieRepo(private val remote: MovieRemoteSource) : MovieDataSource {

    override suspend fun getMoviesData(): GenericResponse<MoviesData> = remote.getMoviesData()
}