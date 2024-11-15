package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovies(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int,
    val total_results: Int
)
