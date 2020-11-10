package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie

interface TmdbMovieToMovieEntityMapper : Mapper<TmdbMovie, MovieEntity>
