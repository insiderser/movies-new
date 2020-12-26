package com.insiderser.popularmovies.model

import androidx.recyclerview.widget.DiffUtil

data class Review(
    val id: String,
    val author: String,
    val content: String
)

object ReviewDiffCallback : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean = oldItem == newItem
}
