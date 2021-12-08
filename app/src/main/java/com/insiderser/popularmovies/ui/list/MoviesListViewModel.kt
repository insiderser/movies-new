package com.insiderser.popularmovies.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.insiderser.popularmovies.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    moviesRepository: MoviesRepository
) : ViewModel() {

    val movies = moviesRepository.getMovies().cachedIn(viewModelScope)
}
