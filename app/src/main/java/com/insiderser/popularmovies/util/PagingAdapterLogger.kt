package com.insiderser.popularmovies.util

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insiderser.popularmovies.BuildConfig
import timber.log.Timber

object PagingAdapterLogger {

    fun bindTo(adapter: PagingDataAdapter<*, *>) {
        if (BuildConfig.DEBUG) {
            adapter.addLoadStateListener { state ->
                Timber.v("New load state: refresh=${state.refresh} append=${state.append} prepend=${state.prepend}")
            }
            adapter.addOnPagesUpdatedListener {
                Timber.v(
                    "Pages updated. First position: ${adapter.snapshot().placeholdersBefore}, last position: ${
                        adapter.snapshot().run { placeholdersBefore + items.size }
                    }"
                )
            }
            adapter.registerAdapterDataObserver(LoggingAdapterDataObserver())
        }
    }

    private class LoggingAdapterDataObserver : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            Timber.i("onItemRangeChanged positionStart=$positionStart itemCount=$itemCount")
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            Timber.i("onItemRangeInserted positionStart=$positionStart itemCount=$itemCount")
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            Timber.i("onItemRangeRemoved positionStart=$positionStart itemCount=$itemCount")
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            Timber.i("onItemRangeMoved fromPosition=$fromPosition toPosition=$toPosition itemCount=$itemCount")
        }
    }
}
