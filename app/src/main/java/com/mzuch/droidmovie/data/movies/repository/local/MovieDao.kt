package com.mzuch.droidmovie.data.movies.repository.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mzuch.droidmovie.data.movies.model.MovieEntity
import com.mzuch.droidmovie.data.movies.model.MovieFavorite
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    fun getAllAsFlow(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Query("DELETE FROM MovieEntity")
    suspend fun deleteAll()

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE id = :uid")
    suspend fun getMovie(uid: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity WHERE isFavorite = 1")
    suspend fun getAllFavorites(): List<MovieEntity>

    @Update(entity = MovieEntity::class)
    suspend fun updateFavoritesAll(movies: List<MovieFavorite>)

    @Query("SELECT * FROM MovieEntity ORDER BY localSortKey ASC")
    fun pagingSource(): PagingSource<Int, MovieEntity>
}