package com.mzuch.droidmovie

import com.mzuch.droidmovie.data.movies.repository.MovieRepo
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemote
import com.mzuch.droidmovie.network.MovieApi
import com.mzuch.droidmovie.network.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val baseUrl = "https://api.themoviedb.org/3"

    @Provides
    @Singleton
    fun provideWildfireApi(): MovieApi {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MovieApi): MovieDataSource {
        val remote = MovieRemote(api)
        return MovieRepo(remote)
    }
}