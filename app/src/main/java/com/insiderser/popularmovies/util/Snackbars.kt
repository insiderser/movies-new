package com.insiderser.popularmovies.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.insiderser.popularmovies.R

fun View.showErrorSnackbar(@StringRes messageRes: Int, onRetry: (() -> Unit)? = null) {
    showErrorSnackbar(context.getText(messageRes), onRetry)
}

fun View.showErrorSnackbar(message: CharSequence, onRetry: (() -> Unit)? = null) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.getColorFromAttr(R.attr.colorError))
        .setTextColor(context.getColorFromAttr(R.attr.colorOnError))
        .apply { if (onRetry != null) setAction(R.string.retry) { onRetry() } }
        .show()
}
