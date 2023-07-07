package com.mzuch.droidmovie.data.movies.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieUpdateFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertAll(vararg movies: MovieEntity)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE uid = :uid")
    suspend fun getMovie(uid: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE isFavorite = 1")
    suspend fun getAllFavorites(): List<MovieEntity>

    @Update(entity = MovieEntity::class)
    suspend fun updateFavoritesAll(movies: List<MovieUpdateFavoriteEntity>)
}