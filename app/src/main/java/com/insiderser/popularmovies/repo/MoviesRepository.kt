package com.insiderser.popularmovies.repo

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.model.MovieBasicInfo
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMovies(pagingConfig: PagingConfig): Flow<PagingData<MovieBasicInfo>>
}
