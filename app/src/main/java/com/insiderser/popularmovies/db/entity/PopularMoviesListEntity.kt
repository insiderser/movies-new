package com.insiderser.popularmovies.db.entity

import androidx.annotation.IntRange

data class PopularMoviesListEntity(
    @IntRange(from = 0) val position: Int,
    val movieId: Int
) {
    init {
        require(position >= 0) { "Position cannot be negative." }
    }
}
