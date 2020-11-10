package com.insiderser.popularmovies.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.ReviewsEntity
import com.insiderser.popularmovies.model.Review

@Dao
interface ReviewsDao {

    @Query(
        """
        SELECT id, author, content
        FROM reviews
        WHERE movieId = :movieId
    """
    )
    fun findReviewsByMovieId(movieId: Int): PagingSource<Int, Review>

    @Insert(onConflict = REPLACE)
    suspend fun save(review: ReviewsEntity)

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(reviews: List<ReviewsEntity>)
}
