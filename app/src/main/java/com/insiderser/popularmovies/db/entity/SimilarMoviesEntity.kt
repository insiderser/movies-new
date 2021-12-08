package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "similarMovies",
    indices = [Index("parentMovieId"), Index("similarMovieId"), Index("position")],
    primaryKeys = ["parentMovieId", "similarMovieId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentMovieId"],
        ),
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["similarMovieId"],
        ),
    ]
)
data class SimilarMoviesEntity(
    val parentMovieId: Int,
    val similarMovieId: Int,
    val position: Int,
)
