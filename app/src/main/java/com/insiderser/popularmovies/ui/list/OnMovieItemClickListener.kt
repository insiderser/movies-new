package com.insiderser.popularmovies.ui.list

import android.view.View
import com.insiderser.popularmovies.model.MoviePoster

fun interface OnMovieItemClickListener {
    operator fun invoke(movie: MoviePoster, rootView: View)
}
