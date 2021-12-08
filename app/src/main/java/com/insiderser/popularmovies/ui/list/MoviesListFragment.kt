package com.insiderser.popularmovies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.insiderser.popularmovies.NavMainDirections
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentMoviesListBinding
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.ui.common.FooterLoadStateAdapter
import com.insiderser.popularmovies.util.applySystemWindowInsetsToProgressOffset
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.getMessageForUser
import com.insiderser.popularmovies.util.toPopularMoviesException
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.flow.map

abstract class MoviesListFragment : Fragment() {

    protected abstract val viewModel: MoviesListViewModel

    private var binding: FragmentMoviesListBinding by viewLifecycleScoped()
    private var moviesAdapter: MoviesListAdapter by viewLifecycleScoped()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMoviesListBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        viewModel.movies.collectWithLifecycle(viewLifecycleOwner) { movies ->
            moviesAdapter.submitData(movies)
        }
    }

    private fun setupUi() {
        binding.swipeRefreshLayout.applySystemWindowInsetsToProgressOffset()
        binding.moviesList.applySystemWindowInsetsToPadding(bottom = true)
        moviesAdapter = MoviesListAdapter(
            onItemClick = ::navigateToMovieDetails
        )
        val loadingAdapter = FooterLoadStateAdapter(retry = moviesAdapter::retry)
        binding.moviesList.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            loadingAdapter, loadingAdapter
        )
        binding.swipeRefreshLayout.setOnRefreshListener(moviesAdapter::refresh)

        observeLoadingState()
    }

    private fun observeLoadingState() {
        val refreshStateFlow = moviesAdapter.loadStateFlow.map { it.refresh }
        refreshStateFlow.collectWithLifecycle(viewLifecycleOwner) { state ->
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

    private fun navigateToMovieDetails(movie: MovieBasicInfo) {
        val direction = NavMainDirections.toMovieDetailsFragment(movieId = movie.id)
        findNavController().navigate(direction)
    }
}
