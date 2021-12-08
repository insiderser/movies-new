package com.insiderser.popularmovies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import com.insiderser.popularmovies.NavMainDirections
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentMoviesListBinding
import com.insiderser.popularmovies.model.MoviePoster
import com.insiderser.popularmovies.ui.common.FooterLoadStateAdapter
import com.insiderser.popularmovies.util.applySystemWindowInsetsToProgressOffset
import com.insiderser.popularmovies.util.getMessageForUser
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.toPopularMoviesException
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private val viewModel: MoviesListViewModel by viewModels()

    private var binding: FragmentMoviesListBinding by viewLifecycleScoped()
    private val moviesAdapter: MoviesListAdapter by lazy {
        MoviesListAdapter(
            onItemClick = ::navigateToMovieDetails
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMoviesListBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        setupUi()

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter.submitData(movies)
            binding.moviesList.doOnPreDraw { startPostponedEnterTransition() }
        }
    }

    private fun setupUi() {
        binding.swipeRefreshLayout.applySystemWindowInsetsToProgressOffset()
        binding.moviesList.applySystemWindowInsetsToPadding(top = true, bottom = true)
        binding.moviesList.adapter = moviesAdapter.withLoadStateFooter(
            FooterLoadStateAdapter(
                retry = moviesAdapter::retry
            )
        )
        binding.swipeRefreshLayout.setOnRefreshListener(moviesAdapter::refresh)

        observeLoadingState()
    }

    private fun observeLoadingState() {
        val refreshStateFlow = moviesAdapter.loadStateFlow.map { it.refresh }
        refreshStateFlow.observe(viewLifecycleOwner) { state ->
            binding.swipeRefreshLayout.isRefreshing = state is LoadState.Loading

            if (state is LoadState.Error) {
                val error = state.error.toPopularMoviesException()
                val message = error.getMessageForUser(requireContext())

                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.retry) { moviesAdapter.retry() }
                    .show()
            }
        }
    }

    private fun navigateToMovieDetails(movie: MoviePoster, view: View) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.animation_duration_long).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.animation_duration_long).toLong()
        }

        val extras = FragmentNavigatorExtras(
            view to getString(R.string.transition_movie_details)
        )

        val direction = NavMainDirections.toMovieDetailsFragment(movieId = movie.id)
        findNavController().navigate(direction, extras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.moviesList.adapter = null
    }
}
