package com.insiderser.popularmovies.ui

import androidx.lifecycle.ViewModel
import com.insiderser.popularmovies.model.Movie
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(

) : ViewModel() {

    val movies: StateFlow<List<Movie>> = TODO()
}
