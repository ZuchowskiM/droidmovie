package com.mzuch.droidmovie.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieFavorite
import com.mzuch.droidmovie.data.movies.paging.MovieRemoteKey
import com.mzuch.droidmovie.data.movies.paging.MovieRemoteKeyDao
import com.mzuch.droidmovie.data.movies.repository.local.MovieDao
import com.mzuch.droidmovie.data.movies.repository.local.MovieFavoriteDao

@Database(entities = [MovieEntity::class, MovieRemoteKey::class, MovieFavorite::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao
    abstract fun movieFavoriteDao(): MovieFavoriteDao
}