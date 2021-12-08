package com.insiderser.popularmovies.repo

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.insiderser.popularmovies.model.MovieBasicInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SearchRepository {

    fun searchMovies(
        pagingConfig: PagingConfig,
        queryFlow: StateFlow<String?>,
        coroutineScope: CoroutineScope,
    ): SearchResult

    data class SearchResult(
        val movies: Flow<PagingData<MovieBasicInfo>>,
        val foundResults: Flow<Boolean>,
    )
}
