package com.insiderser.popularmovies.ui.reviews

import androidx.recyclerview.widget.RecyclerView
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.ItemReviewBinding
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.util.parseAsMarkdown

class ReviewViewHolder(
    private val binding: ItemReviewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Review?) = with(binding) {
        val context = root.context
        author.text = context.getString(R.string.review_author, item?.author)
        content.text = item?.content?.parseAsMarkdown(context)
    }
}
