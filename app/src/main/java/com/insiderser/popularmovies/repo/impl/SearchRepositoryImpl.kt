package com.insiderser.popularmovies.repo.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.insiderser.popularmovies.repo.SearchRepository
import com.insiderser.popularmovies.repo.SearchRepository.SearchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchMoviesPagingSourceFactory: SearchMoviesPagingSource.Factory,
    private val emptyMoviesPagingSourceFactory: EmptyMoviesPagingSource.Factory,
) : SearchRepository {

    override fun searchMovies(
        pagingConfig: PagingConfig,
        queryFlow: StateFlow<String?>,
        coroutineScope: CoroutineScope,
    ): SearchResult {
        var currentPagingSource: PagingSource<*, *>? = null
        val foundResults = MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                val query = queryFlow.value
                if (query != null) {
                    searchMoviesPagingSourceFactory.create(
                        query,
                        onWhetherFoundResults = { found ->
                            foundResults.emit(found)
                        }
                    )
                } else {
                    emptyMoviesPagingSourceFactory.create()
                }.also { source ->
                    currentPagingSource = source
                }
            },
        )

        coroutineScope.launch {
            queryFlow.collect { currentPagingSource?.invalidate() }
        }

        return SearchResult(
            movies = pager.flow,
            foundResults = foundResults,
        )
    }
}
