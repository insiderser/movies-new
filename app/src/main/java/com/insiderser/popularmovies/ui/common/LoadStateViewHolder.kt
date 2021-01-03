package com.insiderser.popularmovies.ui.common

import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.insiderser.popularmovies.databinding.ItemLoadStateErrorBinding
import com.insiderser.popularmovies.databinding.ItemLoadStateLoadingBinding

sealed class LoadStateViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.updateLayoutParams {
            if (this is StaggeredGridLayoutManager.LayoutParams) {
                isFullSpan = true
            }
        }
    }

    class Loading(
        binding: ItemLoadStateLoadingBinding
    ) : LoadStateViewHolder(binding)

    class Error(
        binding: ItemLoadStateErrorBinding,
        retry: () -> Unit
    ) : LoadStateViewHolder(binding) {

        init {
            binding.retryBtn.setOnClickListener { retry() }
        }
    }
}
