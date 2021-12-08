package com.insiderser.popularmovies.ui.list.popular

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.insiderser.popularmovies.repo.MoviesRepository
import com.insiderser.popularmovies.ui.common.PAGING_CONFIG_MOVIES
import com.insiderser.popularmovies.ui.list.MoviesListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
) : MoviesListViewModel() {

    override val movies = moviesRepository.getMovies(PAGING_CONFIG_MOVIES)
        .cachedIn(viewModelScope)
}
