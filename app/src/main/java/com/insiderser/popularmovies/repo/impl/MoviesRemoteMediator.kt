package com.insiderser.popularmovies.repo.impl

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.mapper.popularListEntityMapper
import com.insiderser.popularmovies.mapper.tmdbMoviesToMovieEntitiesMapper
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import com.insiderser.popularmovies.rest.tmdb.TmdbConfig
import timber.log.Timber
import javax.inject.Inject

class MoviesRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val moviesDao: MoviesDao,
    private val popularMoviesListDao: PopularMoviesListDao,
    private val moviesService: MoviesService,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val positionToLoad = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.APPEND -> popularMoviesListDao.getLastInsertedPosition()!! + 1
            LoadType.PREPEND -> {
                val firstInsertedPosition = popularMoviesListDao.getFirstInsertedPosition()!!
                if (firstInsertedPosition <= 0) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                firstInsertedPosition - TmdbConfig.PAGE_SIZE
            }
        }
        val pageToLoad = getPageForPosition(positionToLoad)

        Timber.d("Loading $loadType position=$positionToLoad page=$pageToLoad")

        val loadedMovies = try {
            moviesService.getPopularMovies(page = pageToLoad)
        } catch (e: Exception) {
            Timber.e(e, "Failure loading popular movies at position $positionToLoad")
            return MediatorResult.Error(e)
        }

        val entities = tmdbMoviesToMovieEntitiesMapper(loadedMovies)
        val entitiesByPosition = popularListEntityMapper(getFirstPositionForPage(pageToLoad)).invoke(entities)

        Timber.d("Got ${loadedMovies.results.size} movies. First position: ${entitiesByPosition.firstOrNull()?.position}. Last position: ${entitiesByPosition.lastOrNull()?.position}")

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                popularMoviesListDao.deleteAll()
            }

            moviesDao.insertAll(entities)
            popularMoviesListDao.insertAll(entitiesByPosition)
        }

        return MediatorResult.Success(
            endOfPaginationReached = entities.size < TmdbConfig.PAGE_SIZE
        )
    }

    private fun getPageForPosition(position: Int): Int = position / TmdbConfig.PAGE_SIZE + 1
    private fun getFirstPositionForPage(page: Int): Int = (page - 1) * TmdbConfig.PAGE_SIZE
}
