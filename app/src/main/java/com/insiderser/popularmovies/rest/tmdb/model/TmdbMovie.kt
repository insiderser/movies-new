package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbMovie(
    val backdrop_path: String?,
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val title: String,
    val vote_average: Float,
)
