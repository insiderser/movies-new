package com.insiderser.popularmovies.ui.list.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.repo.SearchRepository
import com.insiderser.popularmovies.ui.common.PAGING_CONFIG_MOVIES
import com.insiderser.popularmovies.ui.list.MoviesListViewModel
import com.insiderser.popularmovies.util.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchMoviesViewModel @Inject constructor(
    searchRepository: SearchRepository,
    private val inputValidator: InputValidator,
) : MoviesListViewModel() {

    private val queryFlow = MutableStateFlow("")

    override val movies: Flow<PagingData<MovieBasicInfo>>
    private val foundMovies: StateFlow<Boolean>

    init {
        val queryFlowForSearch = queryFlow.map { query ->
            query.takeIf { inputValidator.isSearchQueryValid(it) }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = "")

        val result = searchRepository.searchMovies(PAGING_CONFIG_MOVIES, queryFlowForSearch, viewModelScope)

        movies = result.movies.cachedIn(viewModelScope)

        foundMovies = result.foundResults
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = false)
    }

    val searchState: Flow<SearchState> = combine(queryFlow, foundMovies) { query, foundMovies ->
        when {
            !inputValidator.isSearchQueryValid(query) -> SearchState.INVALID_QUERY
            !foundMovies -> SearchState.NO_RESULTS
            else -> SearchState.VALID
        }
    }

    fun onQuerySubmitted(query: String) {
        queryFlow.value = query
    }

    fun onQueryUpdated(query: String) {
        if (!inputValidator.isSearchQueryValid(query)) {
            queryFlow.value = query
        }
    }

    enum class SearchState {
        VALID, INVALID_QUERY, NO_RESULTS
    }
}
