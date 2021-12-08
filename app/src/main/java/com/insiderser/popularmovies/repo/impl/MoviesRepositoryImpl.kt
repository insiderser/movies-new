package com.insiderser.popularmovies.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.mapper.asPagingListMapper
import com.insiderser.popularmovies.mapper.movieBasicInfoMapper
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val popularMoviesListDao: PopularMoviesListDao,
    private val moviesRemoteMediator: MoviesRemoteMediator
) : MoviesRepository {

    override fun getMovies(pagingConfig: PagingConfig): Flow<PagingData<MovieBasicInfo>> {
        val pager = Pager(
            config = pagingConfig,
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = { popularMoviesListDao.findAllMovies() }
        )
        return pager.flow.map { movieBasicInfoMapper.asPagingListMapper().invoke(it) }
    }
}
