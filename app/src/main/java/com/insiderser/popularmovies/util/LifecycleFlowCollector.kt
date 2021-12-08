package com.insiderser.popularmovies.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Launch a coroutine and collect this flow while
 * given [lifecycle] is in the [minActiveState] state or higher.
 *
 * The collection will be automatically cancelled and restarted when
 * [lifecycle] moves below the [minActiveState] and back respectively.
 *
 * @param collector The function that will be called for every item
 * when [lifecycle] is in at least the [minActiveState] state.
 */
fun <T> Flow<T>.collectWithLifecycle(
    lifecycle: Lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) {
    lifecycle.coroutineScope.launch {
        flowWithLifecycle(lifecycle, minActiveState).collectLatest(collector)
    }
}

/**
 * Launch a coroutine and collect this flow while
 * given [lifecycleOwner] is in the [minActiveState] state or higher.
 *
 * The collection will be automatically cancelled and restarted when
 * [lifecycleOwner] moves below the [minActiveState] and back respectively.
 *
 * @param collector The function that will be called for every item
 * when [lifecycleOwner] is in at least the [minActiveState] state.
 */
fun <T> Flow<T>.collectWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend (T) -> Unit
) = collectWithLifecycle(lifecycleOwner.lifecycle, minActiveState, collector)
