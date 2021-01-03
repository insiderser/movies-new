package com.insiderser.popularmovies.db.dao.impl

import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.InvalidationTracker
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.mapper.cursorToMovieEntityMapper
import com.insiderser.popularmovies.mapper.map
import com.insiderser.popularmovies.mapper.movieEntityToContentValuesMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesDaoImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : MoviesDao {

    override fun getMovieById(movieId: Int): Flow<MovieEntity> = callbackFlow<MovieEntity> {
        suspend fun emitMovie() {
            val cursor = appDatabase.query(DbContract.Movies.QUERY_MOVIE_BY_ID, arrayOf(movieId))
            offer(cursor.map(cursorToMovieEntityMapper).single())
        }

        emitMovie()
        val invalidationTracker = InvalidationTracker { emitMovie() }

        appDatabase.addInvalidationTracker(invalidationTracker)
        awaitClose { appDatabase.removeInvalidationTracker(invalidationTracker) }
    }
        .flowOn(Dispatchers.IO)

    override suspend fun insert(movie: MovieEntity) {
        appDatabase.insert(
            table = DbContract.Movies.TABLE_NAME,
            values = movieEntityToContentValuesMapper(movie),
        )
    }

    override suspend fun insertAll(movies: List<MovieEntity>) = appDatabase.inTransaction {
        movies.forEach { insert(it) }
    }
}
