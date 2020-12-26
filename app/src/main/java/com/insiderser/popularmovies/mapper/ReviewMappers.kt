package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.ReviewsEntity
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.rest.tmdb.model.TmdbReview
import com.insiderser.popularmovies.rest.tmdb.model.TmdbReviews

fun tmdbReviewsMapper(movieId: Int) = Mapper.build<TmdbReviews, List<ReviewsEntity>> {
    results.map(tmdbReviewMapper(movieId))
}

fun tmdbReviewMapper(movieId: Int) = Mapper.build<TmdbReview, ReviewsEntity> {
    ReviewsEntity(author, content, movieId, id)
}

val reviewMapper = Mapper.build<ReviewsEntity, Review> {
    Review(id, author, content)
}
