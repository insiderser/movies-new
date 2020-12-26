package com.insiderser.popularmovies.ui.reviews

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.repo.ReviewsRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class ReviewsViewModel @ViewModelInject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private val currentMovieIdFlow = MutableSharedFlow<Int>(replay = 1)

    val reviews = currentMovieIdFlow
        .distinctUntilChanged()
        .flatMapLatest { movieId -> reviewsRepository.getReviews(movieId) }
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    fun init(movieId: Int) {
        viewModelScope.launch {
            currentMovieIdFlow.emit(movieId)
            reviewsRepository.fetchReviews(movieId)
        }
    }
}
