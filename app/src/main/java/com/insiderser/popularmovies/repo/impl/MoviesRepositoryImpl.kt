package com.insiderser.popularmovies.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val popularMoviesListDao: PopularMoviesListDao,
    private val moviesRemoteMediator: MoviesRemoteMediator
) : MoviesRepository {

    // TODO: extract this to PagingInteractor.
    override fun getMovies(): Flow<PagingData<Movie>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = { popularMoviesListDao.findAllMovies() }
        )
        return pager.flow
    }
}
