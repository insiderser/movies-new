package com.insiderser.popularmovies.util

import androidx.paging.PagingData
import androidx.paging.flatMap

inline fun <T : Any, R : Any> PagingData<T>.mapNotNull(
    crossinline transform: suspend (T) -> R?
) = flatMap { item ->
    val result = transform(item)
    if (result != null) {
        listOf(result)
    } else {
        emptyList()
    }
}
