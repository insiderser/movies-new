package com.insiderser.popularmovies.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentReviewsBinding
import com.insiderser.popularmovies.util.consume
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class ReviewsFragment : Fragment() {

    private var binding: FragmentReviewsBinding by viewLifecycleScoped()

    private val viewModel: ReviewsViewModel by viewModels()

    private val reviewsAdapter by lazy {
        ReviewsAdapter()
    }

    private val navArgs: ReviewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentReviewsBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.applySystemWindowInsetsToPadding(top = true)
        binding.reviewsList.applySystemWindowInsetsToPadding(bottom = true)
        binding.reviewsList.adapter = reviewsAdapter

        binding.toolbar.setOnMenuItemClickListener { handleMenuClick(it) }
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        viewModel.init(navArgs.movieId)
        viewModel.reviews.observe(viewLifecycleOwner, reviewsAdapter::submitList)
    }

    private fun handleMenuClick(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.addReview -> consume { navigateToAddReview() }
        else -> false
    }

    private fun navigateToAddReview() {
        val direction = ReviewsFragmentDirections.toAddReviewFragment(navArgs.movieId)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.reviewsList.adapter = null
    }
}
