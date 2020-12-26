package com.insiderser.popularmovies.ui.reviews.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.repo.ReviewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.properties.Delegates

class AddReviewViewModel @ViewModelInject constructor(
    private val reviewsRepository: ReviewsRepository,
) : ViewModel() {

    private var currentMovieId: Int by Delegates.notNull()

    private val _navigateBack = MutableSharedFlow<Unit>()
    val navigateBack: Flow<Unit> get() = _navigateBack

    fun init(movieId: Int) {
        currentMovieId = movieId
    }

    fun onCreateReviewClick(author: String, content: String) {
        viewModelScope.launch {
            val id = UUID.randomUUID().toString()
            val review = Review(id, author, content)
            reviewsRepository.createReview(review, currentMovieId)
            _navigateBack.emit(Unit)
        }
    }
}
