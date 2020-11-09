package com.insiderser.popularmovies.repo.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.mapper.TmdbMovieToMovieEntityMapper
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import com.insiderser.popularmovies.rest.tmdb.TmdbConfig
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val moviesDao: MoviesDao,
    private val popularMoviesListDao: PopularMoviesListDao,
    private val moviesService: MoviesService,
    private val tmdbMovieToMovieEntityMapper: TmdbMovieToMovieEntityMapper
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): MediatorResult {
        val positionToLoad = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.APPEND -> popularMoviesListDao.getLastInsertedPosition()!! + 1
            LoadType.PREPEND -> {
                val firstInsertedPosition = popularMoviesListDao.getFirstInsertedPosition()!!
                if (firstInsertedPosition == 0) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                firstInsertedPosition - 1
            }
        }
        val pageToLoad = getPageForPosition(positionToLoad)

        val loadedMovies = try {
            moviesService.getPopularMovies(page = pageToLoad)
        } catch (e: Exception) {
            Timber.e(e)
            return MediatorResult.Error(e)
        }

        val entities = loadedMovies.results.map { tmdbMovieToMovieEntityMapper.map(it) }
        val entitiesByPosition = entities.mapIndexed { index, movieEntity ->
            PopularMoviesListEntity(
                position = positionToLoad + index,
                movieId = movieEntity.id
            )
        }

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                popularMoviesListDao.deleteAll()
            }

            moviesDao.insertAll(entities)
            popularMoviesListDao.insertAll(entitiesByPosition)
        }

        return MediatorResult.Success(endOfPaginationReached = pageToLoad == loadedMovies.total_pages)
    }

    private fun getPageForPosition(position: Int): Int = position / TmdbConfig.PAGE_SIZE + 1
}
