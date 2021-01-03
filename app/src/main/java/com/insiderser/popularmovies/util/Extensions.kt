package com.insiderser.popularmovies.util

inline fun consume(action: () -> Unit): Boolean {
    action()
    return true
}

fun Int.ensureMultipleOf(other: Int) = (this / other) * other
