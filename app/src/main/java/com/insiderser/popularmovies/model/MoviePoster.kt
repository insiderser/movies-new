package com.insiderser.popularmovies.model

import androidx.recyclerview.widget.DiffUtil

data class MoviePoster(
    val id: Int,
    val title: String,
    val posterPath: String?,
)

object MoviePosterDiffCallback : DiffUtil.ItemCallback<MoviePoster>() {

    override fun areItemsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MoviePoster, newItem: MoviePoster): Boolean =
        oldItem == newItem
}
