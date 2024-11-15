package com.insiderser.popularmovies.repo

import com.insiderser.popularmovies.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    fun observeReviews(movieId: Int): Flow<List<Review>>
    suspend fun fetchReviews(movieId: Int)
}
