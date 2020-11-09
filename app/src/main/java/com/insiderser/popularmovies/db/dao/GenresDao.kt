package com.insiderser.popularmovies.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.model.Genre

@Dao
interface GenresDao {

    @Query(
        """
        SELECT genres.id, genres.name
        FROM genres
                 JOIN movieGenres mg ON genres.id = mg.genreId
        WHERE movieId = :movieId
    """
    )
    fun findGenresByMovieId(movieId: Int): PagingSource<Int, Genre>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)
}
