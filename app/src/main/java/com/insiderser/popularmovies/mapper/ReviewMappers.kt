package com.insiderser.popularmovies.mapper

import android.content.ContentValues
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.insiderser.popularmovies.db.entity.ReviewsEntity
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.rest.tmdb.model.TmdbReview
import com.insiderser.popularmovies.rest.tmdb.model.TmdbReviews
import com.insiderser.popularmovies.util.getInt
import com.insiderser.popularmovies.util.getString

fun tmdbReviewsMapper(movieId: Int) = Mapper.build<TmdbReviews, List<ReviewsEntity>> {
    results.map(tmdbReviewMapper(movieId))
}

fun tmdbReviewMapper(movieId: Int) = Mapper.build<TmdbReview, ReviewsEntity> {
    ReviewsEntity(author, content, movieId, id)
}

val reviewMapper = Mapper.build<ReviewsEntity, Review> {
    Review(id, author, content)
}

fun reviewToEntityMapper(movieId: Int) = Mapper.build<Review, ReviewsEntity> {
    ReviewsEntity(author, content, movieId, id)
}

val reviewsEntityToContentValuesMapper = Mapper.build<ReviewsEntity, ContentValues> {
    contentValuesOf(
        "id" to id,
        "author" to author,
        "content" to content,
        "movieId" to movieId,
    )
}

val cursorToReviewsEntityMapper = Mapper.build<Cursor, ReviewsEntity> {
    ReviewsEntity(
        id = getString("id"),
        author = getString("author"),
        content = getString("content"),
        movieId = getInt("movieId"),
    )
}
