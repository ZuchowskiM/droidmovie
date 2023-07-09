package com.mzuch.droidmovie.network

import com.mzuch.droidmovie.data.movies.model.MoviesData
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getMoviesData(
        @Query("page") page: Int,
    ): GenericResponse<MoviesData>
}