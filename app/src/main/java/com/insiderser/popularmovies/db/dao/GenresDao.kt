package com.insiderser.popularmovies.db.dao

import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenresDao {

    fun findGenresByMovieId(movieId: Int): Flow<List<Genre>>
    suspend fun insertAll(genres: List<GenreEntity>)
    suspend fun insertAllMovieGenres(genres: List<MovieGenresEntity>)
}
