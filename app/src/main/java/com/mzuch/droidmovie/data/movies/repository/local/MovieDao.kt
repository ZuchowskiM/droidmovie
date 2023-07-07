package com.mzuch.droidmovie.data.movies.repository.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAll(): Flow<List<MovieEntity>>

    @Upsert(entity = MovieEntity::class)
    suspend fun insertAll(vararg movies: MovieUpdateEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE uid = :uid")
    suspend fun getMovie(uid: Int): MovieEntity
}