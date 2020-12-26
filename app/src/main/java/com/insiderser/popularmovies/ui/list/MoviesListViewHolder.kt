package com.insiderser.popularmovies.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.ItemMovieBinding
import com.insiderser.popularmovies.model.MoviePoster
import com.insiderser.popularmovies.util.loadPoster

class MoviesListViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClick: OnMovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: MoviePoster? = null

    init {
        binding.image.setOnClickListener {
            currentMovie?.let { onItemClick(it, binding.image) }
        }
    }

    fun bind(movie: MoviePoster?) = with(binding) {
        currentMovie = movie
        image.contentDescription = movie?.title

        image.loadPoster(movie?.posterPath) {
            crossfade(true)
        }

        image.transitionName = root.context.getString(
            R.string.transition_list_movies_item,
            movie?.id.toString()
        )
    }
}
