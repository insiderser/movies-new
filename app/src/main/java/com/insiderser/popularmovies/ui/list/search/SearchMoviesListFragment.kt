package com.insiderser.popularmovies.ui.list.search

import androidx.fragment.app.viewModels
import com.insiderser.popularmovies.ui.list.MoviesListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesListFragment : MoviesListFragment() {

    override val viewModel: SearchMoviesViewModel by viewModels({ parentFragment ?: this })
}
