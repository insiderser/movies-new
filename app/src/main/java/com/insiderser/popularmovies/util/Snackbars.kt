package com.insiderser.popularmovies.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.insiderser.popularmovies.R

inline fun View.showErrorSnackbar(@StringRes messageRes: Int, crossinline onRetry: () -> Unit) {
    showErrorSnackbar(context.getText(messageRes), onRetry)
}

inline fun View.showErrorSnackbar(message: CharSequence, crossinline onRetry: () -> Unit) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setBackgroundTint(context.getColorFromAttr(R.attr.colorError))
        .setTextColor(context.getColorFromAttr(R.attr.colorOnError))
        .setAction(R.string.retry) { onRetry() }
        .show()
}
