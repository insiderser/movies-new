package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovieDetails(
    val backdrop_path: String?,
    val genres: List<TmdbGenre>,
    val id: Int,
    val overview: String?,
    val poster_path: String?,
    val production_companies: List<TmdbProductionCompany>,
    val title: String,
    val vote_average: Float,
    val similar: TmdbMovies,
)
