package com.insiderser.popularmovies.ui.details

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.util.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MovieDetailsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val movieDetailsRepository: MovieDetailsRepository,
    private val favoriteMoviesRepository: FavoriteMoviesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = MovieDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _state = MutableStateFlow(MovieDetailsState())
    val state = _state.asStateFlow()

    init {
        movieDetailsRepository.observeMovieDetails(navArgs.movieId)
            .onEach { movieDetails -> _state.update { it.copy(movie = movieDetails) } }
            .launchIn(viewModelScope)

        loadMovieDetails()
    }

    fun refresh() = loadMovieDetails()

    private fun loadMovieDetails() {
        if (state.value.isLoading) return
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            runCatching {
                movieDetailsRepository.loadMovieDetails(navArgs.movieId)
            }.onFailure {
                val message = UserMessage(
                    message = context.getString(R.string.failed_to_load),
                    retry = { refresh() },
                )
                _state.update { it.copy(messages = it.messages + message) }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun toggleIsMovieInFavorites() {
        viewModelScope.launch {
            val movie = state.map { it.movie }.filterNotNull().first()
            if (movie.isFavorite) {
                favoriteMoviesRepository.removeMovieToFavorites(movie)
            } else {
                favoriteMoviesRepository.addMovieToFavorites(movie)
            }
        }
    }

    fun onMessageShown(message: UserMessage) {
        _state.update {
            it.copy(messages = it.messages.filter { it.id != message.id })
        }
    }
}
