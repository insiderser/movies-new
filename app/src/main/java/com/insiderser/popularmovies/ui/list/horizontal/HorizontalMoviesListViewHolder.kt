package com.insiderser.popularmovies.ui.list.horizontal

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.insiderser.popularmovies.databinding.ItemMovieHorizontalBinding
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.ui.list.OnMovieItemClickListener
import com.insiderser.popularmovies.util.loadPoster

class HorizontalMoviesListViewHolder(
    private val binding: ItemMovieHorizontalBinding,
    private val onItemClick: OnMovieItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieBasicInfo?) {
        binding.image.contentDescription = item?.title

        binding.image.loadPoster(item?.posterPath) {
            transition(DrawableTransitionOptions.withCrossFade())
        }

        binding.image.setOnClickListener { item?.let { onItemClick(it) } }
    }
}
