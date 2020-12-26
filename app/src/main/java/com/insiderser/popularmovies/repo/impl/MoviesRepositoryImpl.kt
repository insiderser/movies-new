package com.insiderser.popularmovies.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.mapper.asPagingListMapper
import com.insiderser.popularmovies.mapper.moviePosterMapper
import com.insiderser.popularmovies.model.MoviePoster
import com.insiderser.popularmovies.repo.MoviesRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Reusable
class MoviesRepositoryImpl @Inject constructor(
    private val popularMoviesListDao: PopularMoviesListDao,
    private val moviesRemoteMediator: MoviesRemoteMediator
) : MoviesRepository {

    override fun getMovies(): Flow<PagingData<MoviePoster>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = { popularMoviesListDao.findAllMovies() }
        )
        return pager.flow.map { moviePosterMapper.asPagingListMapper().invoke(it) }
    }
}
