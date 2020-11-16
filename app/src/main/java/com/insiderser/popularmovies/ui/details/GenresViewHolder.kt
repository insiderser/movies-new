package com.insiderser.popularmovies.ui.details

import androidx.recyclerview.widget.RecyclerView
import com.insiderser.popularmovies.databinding.ItemGenreBinding
import com.insiderser.popularmovies.model.Genre

class GenresViewHolder(
    private val binding: ItemGenreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre?) {
        binding.chip.text = genre?.name
    }
}
