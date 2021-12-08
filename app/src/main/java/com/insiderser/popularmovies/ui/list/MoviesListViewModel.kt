package com.insiderser.popularmovies.ui.list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.insiderser.popularmovies.model.MovieBasicInfo
import kotlinx.coroutines.flow.Flow

abstract class MoviesListViewModel : ViewModel() {

    abstract val movies: Flow<PagingData<MovieBasicInfo>>
}
