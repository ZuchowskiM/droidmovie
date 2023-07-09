package com.mzuch.droidmovie.data.movies.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mzuch.droidmovie.data.movies.model.MovieFavorite

@Dao
interface MovieFavoriteDao {

    @Query("SELECT * FROM MovieFavorite")
    suspend fun getAllFavorite(): List<MovieFavorite>

    @Insert
    suspend fun insert(movieFavorite: MovieFavorite)

    @Query("DELETE FROM MovieFavorite WHERE id = :id")
    suspend fun delete(id: Int)
}