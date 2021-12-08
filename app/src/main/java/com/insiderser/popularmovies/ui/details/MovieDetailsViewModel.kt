package com.insiderser.popularmovies.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = MovieDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val movieDetails: SharedFlow<Movie> = movieDetailsRepository.observeMovieDetails(navArgs.movieId)
        .shareIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch {
            movieDetailsRepository.loadMovieDetails(navArgs.movieId)
        }
    }

    fun toggleIsMovieInFavorites() {
        viewModelScope.launch {
            val movie = movieDetails.first()
            if (movie.isFavorite) {
                favoriteMoviesRepository.removeMovieToFavorites(movie)
            } else {
                favoriteMoviesRepository.addMovieToFavorites(movie)
            }
        }
    }
}
