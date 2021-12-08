package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.ReviewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewsDao {

    @Query(
        """
        SELECT *
        FROM reviews
        WHERE movieId = :movieId
    """
    )
    fun findReviewsByMovieId(movieId: Int): Flow<List<ReviewsEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun saveAll(reviews: List<ReviewsEntity>)

    @Query(
        """
            DELETE FROM reviews
            WHERE movieId = :movieId
        """
    )
    suspend fun deleteAllForMovie(movieId: Int)
}
