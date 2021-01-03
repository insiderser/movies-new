package com.insiderser.popularmovies.db.entity

data class ReviewsEntity(
    val author: String,
    val content: String,
    val movieId: Int,
    val id: String
)
