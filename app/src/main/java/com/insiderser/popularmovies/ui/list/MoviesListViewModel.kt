package com.insiderser.popularmovies.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesListViewModel @ViewModelInject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    // TODO: add exception handling.
    val movies: Flow<PagingData<Movie>> = moviesRepository.getMovies()
        .cachedIn(viewModelScope)
}
