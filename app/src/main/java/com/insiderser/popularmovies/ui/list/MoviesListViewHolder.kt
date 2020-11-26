package com.insiderser.popularmovies.ui.list

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.ItemMovieBinding
import com.insiderser.popularmovies.model.Movie

class MoviesListViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClick: OnMovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: Movie? = null

    init {
        binding.image.setOnClickListener {
            currentMovie?.let { onItemClick(it, binding.image) }
        }
    }

    fun bind(movie: Movie?) = with(binding) {
        currentMovie = movie
        image.contentDescription = movie?.title

        image.load(movie?.posterPath) {
            crossfade(true)
        }

        image.transitionName = root.context.getString(
            R.string.transition_list_movies_item,
            movie?.id.toString()
        )
    }
}
