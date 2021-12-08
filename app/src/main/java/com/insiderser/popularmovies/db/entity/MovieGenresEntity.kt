package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movieGenres",
    indices = [Index("movieId"), Index("genreId")],
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"]
        )
    ]
)
data class MovieGenresEntity(
    val movieId: Int,
    val genreId: Int
)
