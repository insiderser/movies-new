package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.model.Genre
import kotlinx.coroutines.flow.Flow

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
    fun findGenresByMovieId(movieId: Int): Flow<List<Genre>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovieGenres(genres: List<MovieGenresEntity>)
}
