package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.insiderser.popularmovies.db.entity.MovieEntity

@Dao
interface MoviesDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)
}
