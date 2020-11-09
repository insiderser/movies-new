package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbReview(
    val author: String,
    val content: String,
    val id: String,
    val url: String
)
