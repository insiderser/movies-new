package com.insiderser.popularmovies.ui.list.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.databinding.FragmentFavoriteMoviesBinding
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private val viewModel: FavoriteMoviesViewModel by viewModels()

    private var binding: FragmentFavoriteMoviesBinding by viewLifecycleScoped()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteMoviesAppBar.applySystemWindowInsetsToPadding(top = true)
        binding.favoriteMoviesToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        viewModel.hasAnyFavorite.collectWithLifecycle(viewLifecycleOwner) { handleHasAnyFavorite(it) }
    }

    private fun handleHasAnyFavorite(hasAnyFavorite: Boolean) {
        binding.noFavoritesContainer.root.isGone = hasAnyFavorite
    }
}
