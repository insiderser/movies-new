package com.insiderser.popularmovies.db.entity

import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "popularMoviesList",
    indices = [Index("movieId")],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        )
    ]
)
data class PopularMoviesListEntity(
    @PrimaryKey @IntRange(from = 0) val position: Int,
    val movieId: Int
) {
    init {
        require(position >= 0) { "Position cannot be negative." }
    }
}
