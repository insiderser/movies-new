package com.insiderser.popularmovies.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.util.EventStatus
import com.insiderser.popularmovies.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
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
        .shareIn(viewModelScope, WhileSubscribed(), replay = 1)

    private val _movieDetailsStatus = MutableStateFlow<EventStatus>(Status.Idle)
    val movieDetailsStatus: Flow<EventStatus> get() = _movieDetailsStatus

    init {
        loadMovieDetails()
    }

    fun refresh() = loadMovieDetails()

    private fun loadMovieDetails() {
        _movieDetailsStatus.value = Status.Loading
        viewModelScope.launch {
            runCatching {
                movieDetailsRepository.loadMovieDetails(navArgs.movieId)
            }
                .onSuccess { _movieDetailsStatus.value = Status.Success() }
                .onFailure { _movieDetailsStatus.value = Status.Failure(it) }
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
