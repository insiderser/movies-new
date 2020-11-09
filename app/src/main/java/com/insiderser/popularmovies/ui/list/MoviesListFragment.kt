package com.insiderser.popularmovies.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.databinding.FragmentMoviesListBinding
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.ui.common.FooterLoadStateAdapter
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint

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
    ): View? = FragmentMoviesListBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        binding.moviesList.doOnPreDraw { startPostponedEnterTransition() }

        binding.moviesList.adapter = moviesAdapter.withLoadStateFooter(
            FooterLoadStateAdapter(
                retry = moviesAdapter::retry
            )
        )

        binding.swipeRefreshLayout.setOnRefreshListener(moviesAdapter::refresh)

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            moviesAdapter.submitData(movies)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun navigateToMovieDetails(movie: Movie, view: View) {
        // TODO: navigate to details with container transform animation.
    }
}
