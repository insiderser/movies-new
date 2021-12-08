package com.insiderser.popularmovies.ui.list

import com.insiderser.popularmovies.model.MovieBasicInfo

fun interface OnMovieItemClickListener {
    operator fun invoke(movie: MovieBasicInfo)
}
