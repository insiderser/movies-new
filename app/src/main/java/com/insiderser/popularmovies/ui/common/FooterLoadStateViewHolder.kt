package com.insiderser.popularmovies.ui.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.insiderser.popularmovies.databinding.ItemLoadStateErrorBinding
import com.insiderser.popularmovies.databinding.ItemLoadStateLoadingBinding

sealed class FooterLoadStateViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class Loading(
        binding: ItemLoadStateLoadingBinding
    ) : FooterLoadStateViewHolder(binding)

    class Error(
        binding: ItemLoadStateErrorBinding,
        retry: () -> Unit
    ) : FooterLoadStateViewHolder(binding) {

        init {
            binding.retryBtn.setOnClickListener { retry() }
        }
    }
}
