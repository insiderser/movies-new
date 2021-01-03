package com.insiderser.popularmovies.db.entity

import androidx.annotation.FloatRange

data class MovieEntity(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    @FloatRange(from = 0.0, to = 10.0) val voteAverage: Float,
)
