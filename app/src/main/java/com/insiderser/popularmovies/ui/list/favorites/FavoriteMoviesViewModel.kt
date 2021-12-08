package com.insiderser.popularmovies.ui.list.favorites

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import com.insiderser.popularmovies.ui.common.PAGING_CONFIG_MOVIES
import com.insiderser.popularmovies.ui.list.MoviesListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    favoriteMoviesRepository: FavoriteMoviesRepository,
) : MoviesListViewModel() {

    override val movies = favoriteMoviesRepository.getFavoriteMovies(PAGING_CONFIG_MOVIES)
        .cachedIn(viewModelScope)

    val hasAnyFavorite = favoriteMoviesRepository.hasAnyFavorite()
}
