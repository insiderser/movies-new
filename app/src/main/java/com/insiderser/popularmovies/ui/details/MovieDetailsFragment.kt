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
import com.insiderser.popularmovies.databinding.FragmentMovieDetailsBinding
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.util.format
import com.insiderser.popularmovies.util.loadPoster
import com.insiderser.popularmovies.util.observe
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var binding: FragmentMovieDetailsBinding by viewLifecycleScoped()

    private var genresAdapter: GenresAdapter by viewLifecycleScoped()
    private var productionCompaniesAdapter: ProductionCompaniesAdapter by viewLifecycleScoped()

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

        binding.scrollView.applySystemWindowInsetsToPadding(top = true, bottom = true)
        binding.reviewsBtn.setOnClickListener { navigateToReviews() }

        genresAdapter = GenresAdapter()
        binding.hero.genresList.adapter = genresAdapter
        binding.hero.genresList.layoutManager = FlexboxLayoutManager(requireContext())

        productionCompaniesAdapter = ProductionCompaniesAdapter()
        binding.productionCompaniesList.adapter = productionCompaniesAdapter

        viewModel.init(navArgs.movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner) { bindMovie(it) }
    }

    private fun bindMovie(movie: Movie) = with(binding) {
        hero.posterImage.loadPoster(movie.posterPath)
        hero.title.text = movie.title
        hero.rating.text = movie.voteAverage.toDouble().format()

        bindGenres(movie.genres)
        bindProductionCompanies(movie.productionCompanies)
    }

    private fun bindGenres(genres: List<Genre>) {
        genresAdapter.submitList(genres)
    }

    private fun bindProductionCompanies(productionCompanies: List<ProductionCompany>) {
        productionCompaniesAdapter.submitList(productionCompanies)
    }

    private fun navigateToReviews() {
        val direction = MovieDetailsFragmentDirections.toReviewsFragment(navArgs.movieId)
        findNavController().navigate(direction)
    }
}
