package com.insiderser.popularmovies.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.insiderser.popularmovies.databinding.ItemMovieBinding
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.model.MovieBasicInfoDiffCallback

class MoviesListAdapter(
    private val onItemClick: OnMovieItemClickListener
) : PagingDataAdapter<MovieBasicInfo, MoviesListViewHolder>(MovieBasicInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return MoviesListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }
}
