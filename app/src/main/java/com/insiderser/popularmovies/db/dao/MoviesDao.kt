package com.insiderser.popularmovies.db.dao

import com.insiderser.popularmovies.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MoviesDao {

    fun getMovieById(movieId: Int): Flow<MovieEntity>
    suspend fun insert(movie: MovieEntity)
    suspend fun insertAll(movies: List<MovieEntity>)
}
