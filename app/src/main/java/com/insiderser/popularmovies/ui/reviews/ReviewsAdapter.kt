package com.insiderser.popularmovies.ui.reviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.insiderser.popularmovies.databinding.ItemReviewBinding
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.model.ReviewDiffCallback

class ReviewsAdapter : ListAdapter<Review, ReviewViewHolder>(ReviewDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReviewBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) = holder.bind(getItem(position))

    override fun onViewRecycled(holder: ReviewViewHolder) {
        super.onViewRecycled(holder)
        holder.bind(null)
    }
}
