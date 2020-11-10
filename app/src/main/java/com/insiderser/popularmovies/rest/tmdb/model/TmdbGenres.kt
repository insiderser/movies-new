package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.Serializable

@Serializable
data class TmdbGenres(
    val genres: List<TmdbGenre>
)
