package com.insiderser.popularmovies.ui.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.repo.ReviewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = ReviewsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val reviews = reviewsRepository.observeReviews(navArgs.movieId)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    init {
        viewModelScope.launch {
            reviewsRepository.fetchReviews(navArgs.movieId)
        }
    }
}
