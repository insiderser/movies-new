package com.insiderser.popularmovies.repo

import androidx.paging.PagingData
import com.insiderser.popularmovies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(): Flow<PagingData<Movie>>
}
