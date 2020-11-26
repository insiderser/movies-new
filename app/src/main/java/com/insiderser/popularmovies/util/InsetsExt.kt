package com.insiderser.popularmovies.util

import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
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

fun View.applyStatusBarHeightToHeight() {
    Insetter.builder()
        .setOnApplyInsetsListener { _, insets, _ ->
            updateLayoutParams {
                height = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            }
        }
        .applyToView(this)
}
