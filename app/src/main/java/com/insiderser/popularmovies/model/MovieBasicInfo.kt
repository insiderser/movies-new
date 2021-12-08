package com.insiderser.popularmovies.model

import androidx.recyclerview.widget.DiffUtil

data class MovieBasicInfo(
    val id: Int,
    val title: String,
    val posterPath: String?,
)

object MovieBasicInfoDiffCallback : DiffUtil.ItemCallback<MovieBasicInfo>() {

    override fun areItemsTheSame(oldItem: MovieBasicInfo, newItem: MovieBasicInfo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieBasicInfo, newItem: MovieBasicInfo): Boolean =
        oldItem == newItem
}
