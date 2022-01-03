package com.insiderser.popularmovies.ui.details

import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.util.UserMessage

data class MovieDetailsState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val messages: List<UserMessage> = emptyList(),
)
