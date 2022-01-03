package com.insiderser.popularmovies.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.db.dao.FavoriteMoviesDao
import com.insiderser.popularmovies.db.entity.FavoriteMovieEntity
import com.insiderser.popularmovies.mapper.asPagingListMapperNotNull
import com.insiderser.popularmovies.mapper.movieBasicInfoMapper
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMoviesRepositoryImpl @Inject constructor(
    private val favoriteMoviesDao: FavoriteMoviesDao,
) : FavoriteMoviesRepository {

    override fun getFavoriteMovies(pagingConfig: PagingConfig): Flow<PagingData<MovieBasicInfo>> {
        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { favoriteMoviesDao.findAllMovies() }
        )
        return pager.flow.map { movieBasicInfoMapper.asPagingListMapperNotNull().invoke(it) }
    }

    override fun hasAnyFavorite(): Flow<Boolean> = favoriteMoviesDao.hasAnyFavorite()

    override suspend fun addMovieToFavorites(movie: Movie) {
        val entity = FavoriteMovieEntity(
            movieId = movie.id,
        )
        favoriteMoviesDao.addToFavorites(entity)
    }

    override suspend fun removeMovieToFavorites(movie: Movie) {
        favoriteMoviesDao.removeFromFavorites(movieId = movie.id)
    }
}
