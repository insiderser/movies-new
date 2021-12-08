package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(
    tableName = "favoriteMovies",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        )
    ]
)
data class FavoriteMovieEntity(
    @PrimaryKey val movieId: Int,
    val timestamp: OffsetDateTime = OffsetDateTime.now(),
)
