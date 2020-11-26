package com.insiderser.popularmovies.repo

import com.insiderser.popularmovies.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(movieId: Int): Flow<MovieDetails>

    suspend fun loadMovieDetails(movieId: Int)
}
