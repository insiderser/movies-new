package com.insiderser.popularmovies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexboxLayoutManager
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentMovieDetailsBinding
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.ui.list.horizontal.HorizontalMoviesListAdapter
import com.insiderser.popularmovies.util.UserMessage
import com.insiderser.popularmovies.util.animateMaxLines
import com.insiderser.popularmovies.util.cancelMaxLinesAnimation
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.format
import com.insiderser.popularmovies.util.loadPoster
import com.insiderser.popularmovies.util.showErrorSnackbar
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding by viewLifecycleScoped()

    private var genresAdapter: GenresAdapter by viewLifecycleScoped()
    private var similarAdapter: HorizontalMoviesListAdapter by viewLifecycleScoped()

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val navArgs: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMovieDetailsBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollView.applySystemWindowInsetsToPadding(bottom = true)
        binding.reviewsBtn.setOnClickListener { navigateToReviews() }
        binding.description.setOnClickListener { toggleDescriptionExpansion() }
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }

        initToolbar()
        initGenres()
        initSimilar()

        observeData()
    }

    private fun observeData() {
        viewModel.state
            .map { it.movie }
            .filterNotNull()
            .collectWithLifecycle(viewLifecycleOwner) { bindMovie(it) }

        viewModel.state
            .map { it.isLoading }
            .distinctUntilChanged()
            .collectWithLifecycle(viewLifecycleOwner) { binding.swipeRefreshLayout.isRefreshing = it }

        viewModel.state
            .map { it.messages.firstOrNull() }
            .filterNotNull()
            .collectWithLifecycle(viewLifecycleOwner) { showMessage(it) }
    }

    private fun initToolbar() {
        binding.movieDetailsAppBarLayout.applySystemWindowInsetsToPadding(top = true)
        binding.movieDetailsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.favoriteToggleView.setOnClickListener { viewModel.toggleIsMovieInFavorites() }
    }

    private fun initGenres() {
        genresAdapter = GenresAdapter()
        binding.hero.genresList.adapter = genresAdapter
        binding.hero.genresList.layoutManager = FlexboxLayoutManager(requireContext())
    }

    private fun initSimilar() {
        similarAdapter = HorizontalMoviesListAdapter(
            onItemClick = ::navigateToMovieDetails
        )
        binding.similarMoviesList.adapter = similarAdapter
    }

    private fun bindMovie(movie: Movie) = with(binding) {
        hero.posterImage.loadPoster(movie.posterPath)
        hero.title.text = movie.title
        hero.rating.text = movie.voteAverage.toDouble().format()
        description.text = movie.overview
        description.isGone = movie.overview.isNullOrEmpty()

        bindIsFavorite(movie.isFavorite)
        bindGenres(movie.genres)
        bindSimilar(movie.similar)
    }

    private fun bindIsFavorite(isFavorite: Boolean) = with(binding.favoriteToggleView) {
        setImageResource(
            if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outlined
        )
        contentDescription = getString(
            if (isFavorite) R.string.remove_from_favorites else R.string.add_to_favorites
        )
    }

    private fun bindGenres(genres: List<Genre>) {
        genresAdapter.submitList(genres)
    }

    private fun bindSimilar(similar: List<MovieBasicInfo>) {
        similarAdapter.submitList(similar)
        binding.similarMoviesHeader.isVisible = similar.isNotEmpty()
    }

    private fun showMessage(message: UserMessage) {
        binding.root.showErrorSnackbar(message.message, message.retry)
        viewModel.onMessageShown(message)
    }

    private fun toggleDescriptionExpansion() {
        binding.description.cancelMaxLinesAnimation()
        val newMaxLines = when (binding.description.maxLines) {
            MAX_LINES_NO_LIMIT -> resources.getInteger(R.integer.description_collapsed_max_lines)
            else -> MAX_LINES_NO_LIMIT
        }
        binding.description.animateMaxLines(newMaxLines)
    }

    private fun navigateToReviews() {
        val direction = MovieDetailsFragmentDirections.toReviewsFragment(navArgs.movieId)
        findNavController().navigate(direction)
    }

    private fun navigateToMovieDetails(movie: MovieBasicInfo) {
        val direction = MovieDetailsFragmentDirections.toMovieDetailsFragment(movie.id)
        findNavController().navigate(direction)
    }

    companion object {
        private const val MAX_LINES_NO_LIMIT = Int.MAX_VALUE
    }
}
