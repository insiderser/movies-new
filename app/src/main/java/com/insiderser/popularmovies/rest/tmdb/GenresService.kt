package com.insiderser.popularmovies.rest.tmdb

import com.insiderser.popularmovies.rest.tmdb.model.TmdbGenres
import retrofit2.http.GET

interface GenresService {

    @GET("/3/genre/movie/list")
    suspend fun getGenres(): TmdbGenres
}
