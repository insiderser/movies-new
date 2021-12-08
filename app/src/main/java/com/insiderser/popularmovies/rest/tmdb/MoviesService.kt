package com.insiderser.popularmovies.rest.tmdb

import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovies
import com.insiderser.popularmovies.rest.tmdb.model.TmdbReviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): TmdbMovies

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): TmdbMovieDetails

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1,
    ): TmdbReviews
}
