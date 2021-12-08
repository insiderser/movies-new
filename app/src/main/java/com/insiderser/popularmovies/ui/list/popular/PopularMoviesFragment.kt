package com.insiderser.popularmovies.ui.list.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.databinding.FragmentPopularMoviesBinding
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private var binding: FragmentPopularMoviesBinding by viewLifecycleScoped()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentPopularMoviesBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.popularMoviesAppBar.applySystemWindowInsetsToPadding(top = true)
        binding.popularMoviesToolbar.setOnMenuItemClickListener { onMenuSelected(it) }
    }

    private fun onMenuSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.favoritesMenuItem -> {
            onFavoritesSelected()
            true
        }
        else -> false
    }

    private fun onFavoritesSelected() {
        findNavController().navigate(PopularMoviesFragmentDirections.toFavoriteMovies())
    }
}
