package com.insiderser.popularmovies.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline collector: suspend (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launchWhenStarted {
        collect(collector)
    }
}
