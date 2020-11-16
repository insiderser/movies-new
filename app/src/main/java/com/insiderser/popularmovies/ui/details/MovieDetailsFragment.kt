package com.insiderser.popularmovies.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentMovieDetailsBinding
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieDetails
import com.insiderser.popularmovies.util.format
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding by viewLifecycleScoped()

    private val genresAdapter = GenresAdapter()

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val navArgs: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentMovieDetailsBinding.inflate(inflater)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.animation_duration_long).toLong()
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.navHostFragment
        }

        binding.scrollView.applySystemWindowInsetsToPadding(top = true, bottom = true)

        binding.hero.genresList.adapter = genresAdapter
        binding.hero.genresList.layoutManager = FlexboxLayoutManager(requireContext())

        viewModel.init(navArgs.movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner, ::bindMovieDetails)
    }

    private fun bindMovieDetails(movieDetails: MovieDetails) {
        bindMovie(movieDetails.movie)
        bindGenres(movieDetails.genres)
    }

    private fun bindMovie(movie: Movie) = with(binding) {
        hero.posterImage.load(movie.posterPath) {
            listener(
                onSuccess = { _, _ -> startPostponedEnterTransition() },
                onError = { _, _ -> startPostponedEnterTransition() },
                onCancel = { startPostponedEnterTransition() }
            )
        }
        hero.title.text = movie.title
        hero.rating.text = movie.voteAverage.toDouble().format()
    }

    private fun bindGenres(genres: List<Genre>) {
        genresAdapter.submitList(genres)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.hero.genresList.adapter = null
    }
}
