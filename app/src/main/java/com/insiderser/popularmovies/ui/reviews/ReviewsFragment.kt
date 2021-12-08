package com.insiderser.popularmovies.ui.reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.insiderser.popularmovies.databinding.FragmentReviewsBinding
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

        binding.reviewsList.applySystemWindowInsetsToPadding(top = true, bottom = true)
        binding.reviewsList.adapter = reviewsAdapter

        viewModel.init(navArgs.movieId)
        viewModel.reviews.observe(viewLifecycleOwner, reviewsAdapter::submitList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.reviewsList.adapter = null
    }
}
