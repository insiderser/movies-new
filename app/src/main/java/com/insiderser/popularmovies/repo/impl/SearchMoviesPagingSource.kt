package com.insiderser.popularmovies.repo.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.mapper.tmdbMoviesToBasicInfoMapper
import com.insiderser.popularmovies.mapper.tmdbMoviesToMovieEntitiesMapper
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.rest.tmdb.SearchService
import com.insiderser.popularmovies.rest.tmdb.TmdbConfig
import timber.log.Timber
import javax.inject.Inject

class SearchMoviesPagingSource(
    private val query: String,
    private val searchService: SearchService,
    private val moviesDao: MoviesDao,
    private val onWhetherFoundResults: suspend (Boolean) -> Unit,
) : PagingSource<Int, MovieBasicInfo>() {

    override fun getRefreshKey(state: PagingState<Int, MovieBasicInfo>): Int? {
        return getPageForPosition(state.anchorPosition ?: 0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieBasicInfo> {
        val page = params.key ?: TmdbConfig.PAGE_DEFAULT

        if (page < TmdbConfig.PAGE_DEFAULT) {
            return LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = page + 1,
            )
        }

        val loadedMovies = try {
            searchService.searchMovies(query = query, page = page)
        } catch (e: Exception) {
            Timber.e(e)
            return LoadResult.Error(e)
        }

        val entities = tmdbMoviesToMovieEntitiesMapper(loadedMovies)
        val data = tmdbMoviesToBasicInfoMapper(loadedMovies)

        moviesDao.insertAll(entities)

        if (page == TmdbConfig.PAGE_DEFAULT) {
            onWhetherFoundResults(data.isNotEmpty())
        }

        return LoadResult.Page(
            data = data,
            prevKey = (page - 1).takeIf { it >= TmdbConfig.PAGE_DEFAULT },
            nextKey = (page + 1).takeIf { page < loadedMovies.total_pages - 1 },
        )
    }

    private fun getPageForPosition(position: Int): Int = position / TmdbConfig.PAGE_SIZE + 1

    class Factory @Inject constructor(
        private val searchService: SearchService,
        private val moviesDao: MoviesDao,
    ) {
        fun create(
            query: String,
            onWhetherFoundResults: suspend (Boolean) -> Unit,
        ) = SearchMoviesPagingSource(query, searchService, moviesDao, onWhetherFoundResults)
    }
}
