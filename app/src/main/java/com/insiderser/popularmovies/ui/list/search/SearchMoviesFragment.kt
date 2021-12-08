package com.insiderser.popularmovies.ui.list.search

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.insiderser.popularmovies.databinding.FragmentSearchMoviesBinding
import com.insiderser.popularmovies.ui.list.search.SearchMoviesViewModel.SearchState
import com.insiderser.popularmovies.util.collectWithLifecycle
import com.insiderser.popularmovies.util.showKeyboard
import com.insiderser.popularmovies.util.viewLifecycleScoped
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull

@AndroidEntryPoint
class SearchMoviesFragment : Fragment() {

    private val viewModel: SearchMoviesViewModel by viewModels()

    private var binding: FragmentSearchMoviesBinding by viewLifecycleScoped()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentSearchMoviesBinding.inflate(inflater, container, false)
            .also { binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchAppBar.applySystemWindowInsetsToPadding(top = true)
        binding.backBtn.setOnClickListener { requireActivity().onBackPressed() }
        binding.clearBtn.setOnClickListener { binding.searchInputField.text = null }

        binding.searchInputField.addTextChangedListener { onInputTextChanged(it) }
        binding.searchInputField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSubmitClicked()
                true
            } else {
                false
            }
        }

        viewModel.searchState.collectWithLifecycle(viewLifecycleOwner) { handleSearchState(it) }
    }

    private fun handleSearchState(state: SearchState) {
        binding.moviesListContainer.isVisible = state == SearchState.VALID
        binding.noQueryContainer.root.isVisible = state == SearchState.INVALID_QUERY
        binding.notFoundContainer.root.isVisible = state == SearchState.NO_RESULTS
    }

    private fun onInputTextChanged(query: Editable?) {
        binding.clearBtn.isGone = query.isNullOrEmpty()
        viewModel.onQueryUpdated(query?.toString() ?: "")
    }

    private fun onSubmitClicked() {
        val query = binding.searchInputField.text?.toString() ?: ""
        viewModel.onQuerySubmitted(query)
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            if (viewModel.searchState.firstOrNull() != SearchState.VALID) {
                delay(100)
                binding.searchInputField.showKeyboard()
            }
        }
    }
}
