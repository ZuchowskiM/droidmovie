package com.mzuch.droidmovie

import android.content.Context
import androidx.room.Room
import com.mzuch.droidmovie.data.database.AppDatabase
import com.mzuch.droidmovie.data.movies.repository.MovieRepo
import com.mzuch.droidmovie.data.movies.repository.MovieDataSource
import com.mzuch.droidmovie.data.movies.repository.local.MovieLocal
import com.mzuch.droidmovie.data.movies.repository.remote.MovieRemote
import com.mzuch.droidmovie.network.AuthInterceptor
import com.mzuch.droidmovie.network.MovieApi
import com.mzuch.droidmovie.network.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private const val baseUrl = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideWildfireApi(): MovieApi {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = AuthInterceptor()
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
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
    fun provideMoviesRepository(api: MovieApi, db: AppDatabase): MovieDataSource {
        val remote = MovieRemote(api)
        val local = MovieLocal(db)
        return MovieRepo(remote, local)
    }

    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "mainDatabase"
        ).build()
    }
}