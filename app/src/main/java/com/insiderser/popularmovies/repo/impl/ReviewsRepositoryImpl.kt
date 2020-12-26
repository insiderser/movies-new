package com.insiderser.popularmovies.repo.impl

import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.mapper.asListMapper
import com.insiderser.popularmovies.mapper.reviewMapper
import com.insiderser.popularmovies.mapper.reviewToEntityMapper
import com.insiderser.popularmovies.mapper.tmdbReviewsMapper
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.repo.ReviewsRepository
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class ReviewsRepositoryImpl @Inject constructor(
    private val reviewsDao: ReviewsDao,
    private val remoteService: MoviesService,
) : ReviewsRepository {

    override fun getReviews(movieId: Int): Flow<List<Review>> = reviewsDao.findReviewsByMovieId(movieId)
        .map { reviewMapper.asListMapper().invoke(it) }

    override suspend fun fetchReviews(movieId: Int) {
        val response = remoteService.getMovieReviews(movieId)
        val reviews = tmdbReviewsMapper(movieId).invoke(response)
        reviewsDao.saveAll(reviews)
    }

    override suspend fun createReview(review: Review, movieId: Int) {
        val entity = reviewToEntityMapper(movieId).invoke(review)
        reviewsDao.save(entity)
    }
}
