package com.insiderser.popularmovies.db.dao.impl

import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.InvalidationTracker
import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.db.entity.ReviewsEntity
import com.insiderser.popularmovies.mapper.cursorToReviewsEntityMapper
import com.insiderser.popularmovies.mapper.map
import com.insiderser.popularmovies.mapper.reviewsEntityToContentValuesMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReviewsDaoImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ReviewsDao {
    override fun findReviewsByMovieId(movieId: Int): Flow<List<ReviewsEntity>> = callbackFlow<List<ReviewsEntity>> {
        suspend fun emitItems() {
            val cursor = appDatabase.query(DbContract.Reviews.QUERY_BY_MOVIE_ID, arrayOf(movieId))
            send(cursor.map(cursorToReviewsEntityMapper))
        }

        emitItems()
        val invalidationTracker = InvalidationTracker { emitItems() }

        appDatabase.addInvalidationTracker(invalidationTracker)
        awaitClose { appDatabase.removeInvalidationTracker(invalidationTracker) }
    }
        .flowOn(Dispatchers.IO)

    override suspend fun save(review: ReviewsEntity) {
        appDatabase.insert(
            table = DbContract.Reviews.TABLE_NAME,
            values = reviewsEntityToContentValuesMapper(review),
        )
    }

    override suspend fun saveAll(reviews: List<ReviewsEntity>) = appDatabase.inTransaction {
        reviews.forEach { save(it) }
    }
}
