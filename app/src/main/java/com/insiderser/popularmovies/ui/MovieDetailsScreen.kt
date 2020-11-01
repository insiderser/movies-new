package com.insiderser.popularmovies.ui

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable

@Composable
fun MovieDetailsScreen(
    movieId: Int
) {
    Text(text = "Content for movie $movieId")
}
