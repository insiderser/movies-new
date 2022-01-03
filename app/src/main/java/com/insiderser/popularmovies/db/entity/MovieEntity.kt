package com.insiderser.popularmovies.db.entity

import androidx.annotation.FloatRange
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    @FloatRange(from = 0.0, to = 10.0) val voteAverage: Float,
) {
    init {
        require(voteAverage in 0f..10f)
    }
}
