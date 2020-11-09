package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbProductionCompany(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)
