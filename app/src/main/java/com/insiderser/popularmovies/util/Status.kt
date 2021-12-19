package com.insiderser.popularmovies.util

sealed interface Status<out T> {
    object Idle : Status<Nothing>

    object Loading : Status<Nothing>

    class Success<T>(val data: T) : Status<T> {
        companion object {
            operator fun invoke() = Success(Unit)
        }
    }

    class Failure(val reason: Throwable) : Status<Nothing>
}

typealias EventStatus = Status<Unit>
