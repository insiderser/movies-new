package com.insiderser.popularmovies.repo

import com.insiderser.popularmovies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun observeMovieDetails(movieId: Int): Flow<Movie>

    suspend fun loadMovieDetails(movieId: Int)
}
