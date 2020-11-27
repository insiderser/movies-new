package com.insiderser.popularmovies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) : ViewModel() {

    private val movieIdFlow = MutableSharedFlow<Int>(replay = 1)

    val movieDetails = movieIdFlow
        .distinctUntilChanged()
        .flatMapLatest { movieDetailsRepository.getMovieDetails(it) }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    fun init(movieId: Int) {
        viewModelScope.launch {
            movieIdFlow.emit(movieId)
        }

        viewModelScope.launch {
            movieDetailsRepository.loadMovieDetails(movieId)
        }
    }
}