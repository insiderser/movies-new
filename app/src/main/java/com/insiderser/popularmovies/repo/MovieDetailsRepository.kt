package com.insiderser.popularmovies.repo

import com.insiderser.popularmovies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(movieId: Int): Flow<Movie>

    suspend fun loadMovieDetails(movieId: Int)
}
