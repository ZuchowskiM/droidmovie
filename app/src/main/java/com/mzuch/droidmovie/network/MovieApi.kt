package com.mzuch.droidmovie.network

import com.mzuch.droidmovie.data.movies.model.MoviesData
import retrofit2.http.GET

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getMoviesData() : GenericResponse<MoviesData>
}