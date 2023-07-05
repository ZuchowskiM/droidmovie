package com.mzuch.droidmovie.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

    private companion object {
        const val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkYTUxM2FmNjdlYzY1MDdlMDI0M2MxODMwZjg4NWQ4ZCIsInN1YiI6IjY0YTViYjE5NWE5OTE1MDBhZGY4OGQyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.HbyGSbxsk4eWbJYaolsoHTu7QuCpazmdrJxM56xu-2c"
    }
}