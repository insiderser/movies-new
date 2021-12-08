package com.insiderser.popularmovies.repo

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieBasicInfo
import kotlinx.coroutines.flow.Flow

interface FavoriteMoviesRepository {

    fun getFavoriteMovies(pagingConfig: PagingConfig): Flow<PagingData<MovieBasicInfo>>
    fun hasAnyFavorite(): Flow<Boolean>

    suspend fun addMovieToFavorites(movie: Movie)
    suspend fun removeMovieToFavorites(movie: Movie)
}
