package com.insiderser.popularmovies.ui.reviews

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.repo.ReviewsRepository
import com.insiderser.popularmovies.util.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class ReviewsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val reviewsRepository: ReviewsRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val navArgs = ReviewsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _state = MutableStateFlow(ReviewsState())
    val state = _state.asStateFlow()

    init {
        reviewsRepository.observeReviews(navArgs.movieId)
            .onEach { reviews -> _state.update { it.copy(reviews = reviews) } }
            .launchIn(viewModelScope)

        loadReviews()
    }

    fun refresh() = loadReviews()

    private fun loadReviews() {
        if (state.value.isLoading) return
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            runCatching {
                reviewsRepository.fetchReviews(navArgs.movieId)
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

    fun onMessageShown(message: UserMessage) {
        _state.update { it ->
            it.copy(messages = it.messages.filter { it.id != message.id })
        }
    }
}
