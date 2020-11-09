package com.insiderser.popularmovies.ui.list

import android.view.View
import com.insiderser.popularmovies.model.Movie

fun interface OnMovieItemClickListener {
    operator fun invoke(movie: Movie, rootView: View)
}
