package com.insiderser.popularmovies.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.insiderser.popularmovies.repo.MoviesRepository

class MoviesListViewModel @ViewModelInject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val movies = moviesRepository.getMovies().cachedIn(viewModelScope)
}
