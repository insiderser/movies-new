package com.insiderser.popularmovies.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.databinding.FragmentReviewsBinding
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.util.UserMessage
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.showErrorSnackbar
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var binding: FragmentReviewsBinding by viewLifecycleScoped()

    private val viewModel: ReviewsViewModel by viewModels()

    private var reviewsAdapter: ReviewsAdapter by viewLifecycleScoped()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentReviewsBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reviewsAppBar.applySystemWindowInsetsToPadding(top = true)
        binding.reviewsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

        reviewsAdapter = ReviewsAdapter()
        binding.reviewsList.applySystemWindowInsetsToPadding(bottom = true)
        binding.reviewsList.adapter = reviewsAdapter

        observeData()
    }

    private fun observeData() {
        viewModel.state
            .map { it.reviews }
            .collectWithLifecycle(viewLifecycleOwner) { handleReviews(it) }

        viewModel.state
            .map { it.isLoading }
            .distinctUntilChanged()
            .collectWithLifecycle(viewLifecycleOwner) { binding.swipeRefreshLayout.isRefreshing = it }

        viewModel.state
            .map { it.messages.firstOrNull() }
            .filterNotNull()
            .collectWithLifecycle(viewLifecycleOwner) { showMessage(it) }
    }

    private fun handleReviews(reviews: List<Review>) {
        reviewsAdapter.submitList(reviews)
        binding.noReviewsContainer.root.isVisible = reviews.isEmpty()
        binding.reviewsList.isVisible = reviews.isNotEmpty()
    }

    private fun showMessage(message: UserMessage) {
        binding.root.showErrorSnackbar(message.message, message.retry)
        viewModel.onMessageShown(message)
    }
}
