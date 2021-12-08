package com.insiderser.popularmovies.ui.list.popular

import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.ui.list.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesListFragment : MoviesListFragment() {

    override val viewModel: PopularMoviesViewModel by viewModels()
}
