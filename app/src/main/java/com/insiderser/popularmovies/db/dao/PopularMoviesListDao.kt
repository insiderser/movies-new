package com.insiderser.popularmovies.db.dao

import androidx.paging.PagingSource
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity

interface PopularMoviesListDao {

    fun findAllMovies(): PagingSource<Int, MovieEntity>
    suspend fun getFirstInsertedPosition(): Int?
    suspend fun getLastInsertedPosition(): Int?
    suspend fun insertAll(list: List<PopularMoviesListEntity>)
    suspend fun deleteAll()
}
