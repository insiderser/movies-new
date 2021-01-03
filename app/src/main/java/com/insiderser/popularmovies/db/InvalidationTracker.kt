package com.insiderser.popularmovies.db

interface InvalidationTracker {
    suspend operator fun invoke()

    companion object {
        inline operator fun invoke(crossinline action: suspend () -> Unit) = object : InvalidationTracker {
            override suspend fun invoke() = action()
        }
    }
}
