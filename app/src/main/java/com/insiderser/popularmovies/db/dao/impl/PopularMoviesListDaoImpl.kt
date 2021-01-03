package com.insiderser.popularmovies.db.dao.impl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.InvalidationTracker
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.mapper.cursorToIntValueMapper
import com.insiderser.popularmovies.mapper.cursorToMovieEntityMapper
import com.insiderser.popularmovies.mapper.map
import com.insiderser.popularmovies.mapper.popularMovieEntityToContentValuesMapper
import com.insiderser.popularmovies.util.ensureMultipleOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PopularMoviesListDaoImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : PopularMoviesListDao {

    private inner class AllMoviesPagingSource : PagingSource<Int, MovieEntity>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> = withContext(Dispatchers.IO) {
            val position = params.key ?: 0
            val shouldPrepend = params is LoadParams.Prepend

            val cursor = appDatabase.query(
                DbContract.PopularMovies.getQueryAll(shouldPrepend),
                arrayOf(position, params.loadSize)
            )
            val movies = cursor.map(cursorToMovieEntityMapper)
            val previousKey = (position - 1).takeIf { it >= 0 }
            val nextKey = (position + movies.size).takeUnless { movies.size < params.loadSize }
            LoadResult.Page(movies, previousKey, nextKey)
        }

        override val jumpingSupported = true

        override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int =
            state.anchorPosition?.ensureMultipleOf(state.config.pageSize) ?: 0
    }

    override fun findAllMovies(): PagingSource<Int, MovieEntity> {
        val source = AllMoviesPagingSource()
        val invalidationTracker = object : InvalidationTracker {
            override suspend fun invoke() {
                source.invalidate()
                appDatabase.removeInvalidationTracker(this)
            }
        }
        appDatabase.addInvalidationTracker(invalidationTracker)
        return source
    }

    private suspend fun insert(item: PopularMoviesListEntity) {
        appDatabase.insert(
            table = DbContract.PopularMovies.TABLE_NAME,
            values = popularMovieEntityToContentValuesMapper(item),
        )
    }

    override suspend fun insertAll(list: List<PopularMoviesListEntity>) = appDatabase.inTransaction {
        list.forEach { insert(it) }
    }

    override suspend fun getFirstInsertedPosition(): Int? = appDatabase.query(
        DbContract.PopularMovies.QUERY_FIRST_POSITION
    ).map(cursorToIntValueMapper).singleOrNull()

    override suspend fun getLastInsertedPosition(): Int? = appDatabase.query(
        DbContract.PopularMovies.QUERY_LAST_POSITION
    ).map(cursorToIntValueMapper).singleOrNull()

    override suspend fun deleteAll() {
        appDatabase.delete(DbContract.PopularMovies.TABLE_NAME)
    }
}
