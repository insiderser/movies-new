package com.insiderser.popularmovies.db.dao

import com.insiderser.popularmovies.db.entity.ReviewsEntity
import kotlinx.coroutines.flow.Flow

interface ReviewsDao {

    fun findReviewsByMovieId(movieId: Int): Flow<List<ReviewsEntity>>
    suspend fun save(review: ReviewsEntity)
    suspend fun saveAll(reviews: List<ReviewsEntity>)
}
