package com.insiderser.popularmovies.di

import androidx.lifecycle.ViewModelProvider
import com.insiderser.popularmovies.PopularMoviesApp
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        ContextModule::class,
    ]
)
interface AppComponent {

    val viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun get() = PopularMoviesApp.get().component
    }
}
