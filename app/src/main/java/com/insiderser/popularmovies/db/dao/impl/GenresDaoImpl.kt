package com.insiderser.popularmovies.db.dao.impl

import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.InvalidationTracker
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.mapper.cursorToGenreMapper
import com.insiderser.popularmovies.mapper.genreEntityToContentValuesMapper
import com.insiderser.popularmovies.mapper.map
import com.insiderser.popularmovies.mapper.movieGenreEntityToContentValuesMapper
import com.insiderser.popularmovies.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GenresDaoImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : GenresDao {

    override fun findGenresByMovieId(movieId: Int): Flow<List<Genre>> = callbackFlow<List<Genre>> {
        suspend fun emitGenres() {
            val cursor = appDatabase.query(DbContract.Genres.QUERY_BY_MOVIE_ID, arrayOf(movieId))
            send(cursor.map(cursorToGenreMapper))
        }

        emitGenres()
        val invalidationTracker = InvalidationTracker { emitGenres() }

        appDatabase.addInvalidationTracker(invalidationTracker)
        awaitClose { appDatabase.removeInvalidationTracker(invalidationTracker) }
    }
        .flowOn(Dispatchers.IO)

    private suspend fun insert(genre: GenreEntity) {
        appDatabase.insert(
            table = DbContract.Genres.TABLE_NAME,
            values = genreEntityToContentValuesMapper(genre),
        )
    }

    private suspend fun insert(movieGenre: MovieGenresEntity) {
        appDatabase.insert(
            table = DbContract.Genres.TABLE_NAME_MOVIE,
            values = movieGenreEntityToContentValuesMapper(movieGenre),
        )
    }

    override suspend fun insertAll(genres: List<GenreEntity>) = appDatabase.inTransaction {
        genres.forEach { insert(it) }
    }

    override suspend fun insertAllMovieGenres(genres: List<MovieGenresEntity>) = appDatabase.inTransaction {
        genres.forEach { insert(it) }
    }
}
