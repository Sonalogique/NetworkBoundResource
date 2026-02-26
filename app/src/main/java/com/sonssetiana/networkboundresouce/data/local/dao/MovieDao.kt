package com.sonssetiana.networkboundresouce.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sonssetiana.networkboundresouce.data.local.entitys.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun selectMovies(): Flow<List<MovieEntity>>

    @Upsert
    suspend fun upsertMovie(entity: MovieEntity)

    @Upsert
    suspend fun upsertMovies(entity: List<MovieEntity>)

    @Query("Delete from movie where movie_id = :id")
    suspend fun deleteMovie(id: Int)

    @Query("Delete from movie")
    suspend fun clearMovie()
}