package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.SimilarMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SimilarMoviesDao {

    @Query(
        """
            SELECT movies.*
            FROM similarMovies similar
                    INNER JOIN movies ON similar.similarMovieId = movies.id
            WHERE similar.parentMovieId = :movieId
            ORDER BY similar.position ASC
        """
    )
    fun getSimilarMoviesForMovie(movieId: Int): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<SimilarMoviesEntity>)

    @Query(
        """
            DELETE FROM similarMovies
            WHERE parentMovieId = :movieId
        """
    )
    suspend fun deleteAllForMovie(movieId: Int)
}
