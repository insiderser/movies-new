package com.insiderser.popularmovies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexboxLayoutManager
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentMovieDetailsBinding
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.format
import com.insiderser.popularmovies.util.loadPoster
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding by viewLifecycleScoped()

    private var genresAdapter: GenresAdapter by viewLifecycleScoped()

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

        initToolbar()
        initGenres()

        viewModel.movieDetails.collectWithLifecycle(viewLifecycleOwner) { bindMovie(it) }
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

    private fun bindMovie(movie: Movie) = with(binding) {
        hero.posterImage.loadPoster(movie.posterPath)
        hero.title.text = movie.title
        hero.rating.text = movie.voteAverage.toDouble().format()

        bindIsFavorite(movie.isFavorite)

        bindGenres(movie.genres)
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

    private fun navigateToReviews() {
        val direction = MovieDetailsFragmentDirections.toReviewsFragment(navArgs.movieId)
        findNavController().navigate(direction)
    }
}
