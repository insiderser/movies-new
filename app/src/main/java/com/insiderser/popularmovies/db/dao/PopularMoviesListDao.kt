package com.insiderser.popularmovies.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity

@Dao
interface PopularMoviesListDao {

    @Query(
        """
        SELECT movies.*
        FROM popularMoviesList
                JOIN movies ON popularMoviesList.movieId = movies.id
        ORDER BY position
    """
    )
    fun findAllMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT MIN(position) FROM popularMoviesList")
    suspend fun getFirstInsertedPosition(): Int?

    @Query("SELECT MAX(position) FROM popularMoviesList")
    suspend fun getLastInsertedPosition(): Int?

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(list: List<PopularMoviesListEntity>)

    @Query("DELETE FROM popularMoviesList")
    suspend fun deleteAll()
}
