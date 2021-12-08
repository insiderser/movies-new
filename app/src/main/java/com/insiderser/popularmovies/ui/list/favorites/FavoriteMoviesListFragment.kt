package com.insiderser.popularmovies.ui.list.favorites

import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.ui.list.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesListFragment : MoviesListFragment() {

    override val viewModel: FavoriteMoviesViewModel by viewModels({ parentFragment ?: this })
}
