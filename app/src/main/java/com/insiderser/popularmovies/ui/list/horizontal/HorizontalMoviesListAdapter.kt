package com.insiderser.popularmovies.ui.list.horizontal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.insiderser.popularmovies.databinding.ItemMovieHorizontalBinding
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.model.MovieBasicInfoDiffCallback
import com.insiderser.popularmovies.ui.list.OnMovieItemClickListener

class HorizontalMoviesListAdapter(
    private val onItemClick: OnMovieItemClickListener,
) : ListAdapter<MovieBasicInfo, HorizontalMoviesListViewHolder>(MovieBasicInfoDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalMoviesListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieHorizontalBinding.inflate(inflater, parent, false)
        return HorizontalMoviesListViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: HorizontalMoviesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: HorizontalMoviesListViewHolder) {
        super.onViewRecycled(holder)
        holder.bind(null)
    }
}
