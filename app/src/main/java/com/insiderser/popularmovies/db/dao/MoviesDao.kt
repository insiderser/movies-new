package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)
}
