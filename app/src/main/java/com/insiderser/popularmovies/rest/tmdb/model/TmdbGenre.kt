package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbGenre(
    val id: Int,
    val name: String
)
