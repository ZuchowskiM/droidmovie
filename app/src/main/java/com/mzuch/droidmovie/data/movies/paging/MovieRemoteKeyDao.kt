package com.mzuch.droidmovie.data.movies.paging

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKey>)

    @Query("SELECT * FROM MovieRemoteKey WHERE movieId = :id")
    suspend fun remoteKeyMovieId(id: Int): MovieRemoteKey?

    @Query("DELETE FROM MovieRemoteKey")
    suspend fun deleteAll()
}