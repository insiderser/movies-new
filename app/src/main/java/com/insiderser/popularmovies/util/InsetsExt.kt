package com.insiderser.popularmovies.util

import androidx.core.view.WindowInsetsCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dev.chrisbanes.insetter.Insetter

fun SwipeRefreshLayout.applySystemWindowInsetsToProgressOffset() {
    val initialOffset = progressViewEndOffset

    Insetter.builder()
        .setOnApplyInsetsListener { _, insets, _ ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            setProgressViewOffset(false, progressViewStartOffset, initialOffset + statusBarHeight)
        }
        .applyToView(this)
}
