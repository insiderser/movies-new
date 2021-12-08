package com.insiderser.popularmovies.repo.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class EmptyMoviesPagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {

    override fun getRefreshKey(state: PagingState<Key, Value>): Key? = null

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> {
        return LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null,
            itemsBefore = 0,
            itemsAfter = 0,
        )
    }

    class Factory @Inject constructor() {
        fun <Key : Any, Value : Any> create() = EmptyMoviesPagingSource<Key, Value>()
    }
}
