package com.insiderser.popularmovies.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.insiderser.popularmovies.databinding.ItemMovieBinding
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.util.loadPoster

class MoviesListViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClick: OnMovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: MovieBasicInfo? = null

    init {
        binding.image.setOnClickListener {
            currentMovie?.let { onItemClick(it) }
        }
    }

    fun bind(movie: MovieBasicInfo?) = with(binding) {
        currentMovie = movie
        image.contentDescription = movie?.title

        image.loadPoster(movie?.posterPath) {
            transition(DrawableTransitionOptions.withCrossFade())
        }
    }
}
