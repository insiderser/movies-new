package com.insiderser.popularmovies.util

import android.content.Context
import io.noties.markwon.Markwon

fun String.parseAsMarkdown(context: Context) = Markwon.create(context).toMarkdown(this)
