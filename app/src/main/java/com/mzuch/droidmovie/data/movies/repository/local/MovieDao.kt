package com.mzuch.droidmovie.data.movies.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertAll(vararg movies: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()
}