package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.BuildConfig
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails

fun TmdbMovie.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = BuildConfig.TMDB_IMAGES_URL + poster_path,
    backdropPath = BuildConfig.TMDB_IMAGES_URL + backdrop_path,
    voteAverage = vote_average
)

fun TmdbMovieDetails.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = BuildConfig.TMDB_IMAGES_URL + poster_path,
    backdropPath = BuildConfig.TMDB_IMAGES_URL + backdrop_path,
    voteAverage = vote_average
)
