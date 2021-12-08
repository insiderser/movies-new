package com.insiderser.popularmovies.model

import androidx.annotation.FloatRange

data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    @FloatRange(from = 0.0, to = 10.0) val voteAverage: Float,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>
) {
    init {
        require(voteAverage in 0f..10f)
    }
}
