package com.insiderser.popularmovies.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

fun ImageView.load(src: Any?, builder: LoadImageBuilder? = null) {
    Glide.with(this)
        .load(src)
        .let { builder?.invoke(it) ?: it }
        .into(this)
}

typealias LoadImageBuilder = RequestBuilder<Drawable>.() -> RequestBuilder<Drawable>
