package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.BuildConfig
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import javax.inject.Inject

class TmdbMovieToMovieEntityMapperImpl @Inject constructor() : TmdbMovieToMovieEntityMapper {
    override fun map(from: TmdbMovie): MovieEntity = MovieEntity(
        id = from.id,
        title = from.title,
        overview = from.overview,
        posterPath = from.poster_path?.let { BuildConfig.TMDB_IMAGES_URL + it },
        backdropPath = from.backdrop_path?.let { BuildConfig.TMDB_IMAGES_URL + it },
        voteAverage = from.vote_average
    )
}
