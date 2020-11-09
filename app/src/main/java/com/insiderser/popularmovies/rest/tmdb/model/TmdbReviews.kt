package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbReviews(
    val id: Int,
    val page: Int,
    val results: List<TmdbReview>,
    val total_pages: Int,
    val total_results: Int
)
