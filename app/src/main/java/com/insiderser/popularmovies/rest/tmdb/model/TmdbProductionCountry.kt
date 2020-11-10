package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbProductionCountry(
    val iso_3166_1: String,
    val name: String
)
