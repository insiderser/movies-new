package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovieDetails(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Long,
    val genres: List<TmdbGenre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Float,
    val poster_path: String?,
    val production_companies: List<TmdbProductionCompany>,
    val production_countries: List<TmdbProductionCountry>,
    val revenue: Long,
    val runtime: Int?,
    val status: TmdbMovieStatus,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
)
