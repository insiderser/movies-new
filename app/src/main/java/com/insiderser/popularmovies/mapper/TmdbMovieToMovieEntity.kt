package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails

fun TmdbMovie.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = poster_path,
    backdropPath = backdrop_path,
    voteAverage = vote_average
)

fun TmdbMovieDetails.toMovieEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = poster_path,
    backdropPath = backdrop_path,
    voteAverage = vote_average
)
