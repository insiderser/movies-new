package com.insiderser.popularmovies.util

inline fun consume(action: () -> Unit): Boolean {
    action()
    return true
}
