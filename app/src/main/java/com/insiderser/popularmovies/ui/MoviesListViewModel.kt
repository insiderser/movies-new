package com.insiderser.popularmovies.ui

import androidx.lifecycle.ViewModel
import com.insiderser.popularmovies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MoviesListViewModel : ViewModel() {

    val movies: StateFlow<List<Movie>> = MutableStateFlow(
        listOf(
            Movie(
                id = 1,
                title = "Movie 1",
                description = "",
                imageUrl = "https://images.unsplash.com/photo-1528183087798-c83d848b0ecd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
            ),
            Movie(
                id = 2,
                title = "Movie 2",
                description = "",
                imageUrl = "https://images.unsplash.com/photo-1528183087798-c83d848b0ecd?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
            )
        )
    )
}
