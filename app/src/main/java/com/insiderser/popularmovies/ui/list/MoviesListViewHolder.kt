package com.insiderser.popularmovies.ui.list

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.insiderser.popularmovies.databinding.ItemMovieBinding
import com.insiderser.popularmovies.model.Movie
import timber.log.Timber

class MoviesListViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClick: OnMovieItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    private var currentMovie: Movie? = null

    init {
        binding.cardView.setOnClickListener { view ->
            currentMovie?.let { onItemClick(it, view) }
        }
    }

    fun bind(movie: Movie?) = with(binding) {
        currentMovie = movie
        image.load(movie?.posterPath) {
            crossfade(true)
            listener(onError = { _, e ->
                Timber.e(e)
                showImage(false)
            })
        }
        image.contentDescription = movie?.title
        title.text = movie?.title
        showImage(movie?.posterPath != null)
    }

    private fun showImage(show: Boolean = true) = with(binding) {
        image.isVisible = show
        title.isVisible = !show
    }
}
