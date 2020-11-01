package com.insiderser.popularmovies.di

import android.content.Context
import com.insiderser.popularmovies.PopularMoviesApp
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val app: PopularMoviesApp) {

    @Provides
    fun provideContext(): Context = app
}
