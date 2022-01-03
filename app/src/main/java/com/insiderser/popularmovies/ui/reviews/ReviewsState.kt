package com.insiderser.popularmovies.ui.reviews

import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.util.UserMessage

data class ReviewsState(
    val reviews: List<Review> = emptyList(),
    val isLoading: Boolean = false,
    val messages: List<UserMessage> = emptyList(),
)
