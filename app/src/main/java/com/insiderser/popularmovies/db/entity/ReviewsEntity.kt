package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Objects

@Entity(
    tableName = "reviews",
    indices = [Index("movieId")],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        )
    ]
)
data class ReviewsEntity(
    val author: String,
    val content: String,
    val movieId: Int,
    @PrimaryKey val id: String = Objects.hash(author, content, movieId).toString()
)
