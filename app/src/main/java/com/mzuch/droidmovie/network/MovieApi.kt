package com.mzuch.droidmovie.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun getProjectData(
        @Path("projectId") projectId: Int,
    ) : GenericResponse<String>
}