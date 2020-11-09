package com.insiderser.popularmovies.rest.tmdb.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TmdbMovieStatus {
    @SerialName("Rumored")
    RUMORED,

    @SerialName("Planned")
    PLANNED,

    @SerialName("In Production")
    IN_PRODUCTION,

    @SerialName("Post Production")
    POST_PRODUCTION,

    @SerialName("Released")
    RELEASED,

    @SerialName("Canceled")
    CANCELLED,
}
