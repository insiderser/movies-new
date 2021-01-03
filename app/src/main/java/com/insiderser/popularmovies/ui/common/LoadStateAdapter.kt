package com.insiderser.popularmovies.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.insiderser.popularmovies.databinding.ItemLoadStateErrorBinding
import com.insiderser.popularmovies.databinding.ItemLoadStateLoadingBinding

class LoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (loadState) {
            LoadState.Loading -> {
                val binding = ItemLoadStateLoadingBinding.inflate(inflater, parent, false)
                LoadStateViewHolder.Loading(binding)
            }
            is LoadState.Error -> {
                val binding = ItemLoadStateErrorBinding.inflate(inflater, parent, false)
                LoadStateViewHolder.Error(binding, retry)
            }
            else -> throw IllegalArgumentException("Load state $loadState is not supported.")
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {}

    override fun getStateViewType(loadState: LoadState): Int =
        if (loadState is LoadState.Error) 0 else 1
}