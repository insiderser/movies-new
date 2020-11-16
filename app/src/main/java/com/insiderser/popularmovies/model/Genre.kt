package com.insiderser.popularmovies.model

import androidx.recyclerview.widget.DiffUtil

data class Genre(
    val id: Int,
    val name: String
)

object GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean = oldItem == newItem
}
