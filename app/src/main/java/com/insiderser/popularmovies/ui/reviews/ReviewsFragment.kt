package com.insiderser.popularmovies.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentReviewsBinding
import com.insiderser.popularmovies.model.Review
import com.insiderser.popularmovies.util.EventStatus
import com.insiderser.popularmovies.util.Status
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.showErrorSnackbar
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

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

        reviewsAdapter = ReviewsAdapter()
        binding.reviewsList.applySystemWindowInsetsToPadding(bottom = true)
        binding.reviewsList.adapter = reviewsAdapter

        viewModel.reviews.collectWithLifecycle(viewLifecycleOwner) { handleReviews(it) }
        viewModel.reviewsStatus.collectWithLifecycle(viewLifecycleOwner) { handleReviewsStatus(it) }
    }

    private fun handleReviews(reviews: List<Review>) {
        reviewsAdapter.submitList(reviews)
        binding.noReviewsContainer.root.isVisible = reviews.isEmpty()
        binding.reviewsList.isVisible = reviews.isNotEmpty()
    }

    private fun handleReviewsStatus(status: EventStatus) {
        when (status) {
            is Status.Failure -> {
                binding.root.showErrorSnackbar(R.string.failed_to_load) { viewModel.refresh() }
            }
            else -> {
            }
        }
    }
}
