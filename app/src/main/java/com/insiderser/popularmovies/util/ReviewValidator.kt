package com.insiderser.popularmovies.util

import dagger.Reusable
import javax.inject.Inject

@Reusable
class ReviewValidator @Inject constructor() {
    fun validateAuthor(author: String) {
        validate(author.length > 2) { ValidationException.TooShortException }
    }

    fun validateContent(content: String) {
        validate(content.trim().length > 2) { ValidationException.TooShortException }
    }

    private fun validate(mustBeTrue: Boolean, errorProducer: () -> Exception) {
        if (!mustBeTrue) {
            throw errorProducer()
        }
    }
}

sealed class ValidationException : RuntimeException() {
    object TooShortException : ValidationException()
}
