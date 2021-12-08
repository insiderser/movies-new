package com.insiderser.popularmovies.rest.tmdb

import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): TmdbMovies
}
