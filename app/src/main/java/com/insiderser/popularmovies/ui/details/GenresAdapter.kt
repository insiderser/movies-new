package com.insiderser.popularmovies.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.insiderser.popularmovies.databinding.ItemGenreBinding
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.GenreDiffCallback

class GenresAdapter : ListAdapter<Genre, GenresViewHolder>(GenreDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGenreBinding.inflate(inflater, parent, false)
        return GenresViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
