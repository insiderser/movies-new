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
import dagger.Reusable
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@Reusable
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

            if (e is HttpException && e.code() == TmdbConfig.PAGE_OUT_OF_RANGE_CODE) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            return MediatorResult.Error(e)
        }

        val entities = tmdbMoviesToMovieEntitiesMapper(loadedMovies)
        val entitiesByPosition = popularListEntityMapper(positionToLoad).invoke(entities)

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                popularMoviesListDao.deleteAll()
            }

            moviesDao.insertAll(entities)
            popularMoviesListDao.insertAll(entitiesByPosition)
        }

        return MediatorResult.Success(
            endOfPaginationReached = false
        )
    }

    private fun getPageForPosition(position: Int): Int = position / TmdbConfig.PAGE_SIZE + 1
}
