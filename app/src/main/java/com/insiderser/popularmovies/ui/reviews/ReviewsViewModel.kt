package com.insiderser.popularmovies.ui.reviews

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.repo.ReviewsRepository
import com.insiderser.popularmovies.util.EventStatus
import com.insiderser.popularmovies.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _reviewsStatus = MutableStateFlow<EventStatus>(Status.Idle)
    val reviewsStatus: Flow<EventStatus> get() = _reviewsStatus

    init {
        loadReviews()
    }

    fun refresh() = loadReviews()

    private fun loadReviews() {
        _reviewsStatus.value = Status.Loading
        viewModelScope.launch {
            runCatching {
                reviewsRepository.fetchReviews(navArgs.movieId)
            }
                .onSuccess { _reviewsStatus.value = Status.Success() }
                .onFailure { _reviewsStatus.value = Status.Failure(it) }
        }
    }
}
