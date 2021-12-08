package com.insiderser.popularmovies.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.FavoriteMovieEntity
import com.insiderser.popularmovies.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMoviesDao {

    @Query(
        """
        SELECT movies.*
        FROM favoriteMovies
                JOIN movies ON favoriteMovies.movieId = movies.id
        ORDER BY timestamp DESC
    """
    )
    fun findAllMovies(): PagingSource<Int, MovieEntity>

    @Query(
        """
            SELECT EXISTS(
                SELECT *
                FROM favoriteMovies
                WHERE movieId = :movieId
            )
        """
    )
    fun isMovieInFavorites(movieId: Int): Flow<Boolean>

    @Query(
        """
            SELECT EXISTS(
                SELECT * FROM favoriteMovies
            )
        """
    )
    fun hasAnyFavorite(): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(favorite: FavoriteMovieEntity)

    @Query(
        """
            DELETE FROM favoriteMovies
            WHERE movieId = :movieId
        """
    )
    suspend fun removeFromFavorites(movieId: Int)
}
