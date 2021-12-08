package com.insiderser.popularmovies.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment

fun View.showKeyboard() {
    requestFocus()
    val inputMethodManager = context.getSystemService<InputMethodManager>() ?: return
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    clearFocus()
    val inputMethodManager = context.getSystemService<InputMethodManager>() ?: return
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.showKeyboard() = (currentFocus ?: window.decorView).showKeyboard()
fun Activity.hideKeyboard() = (currentFocus ?: window.decorView).hideKeyboard()

fun Fragment.showKeyboard() = requireActivity().showKeyboard()
fun Fragment.hideKeyboard() = requireActivity().hideKeyboard()
