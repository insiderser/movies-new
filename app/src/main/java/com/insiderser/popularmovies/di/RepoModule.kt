package com.insiderser.popularmovies.di

import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.repo.MoviesRepository
import com.insiderser.popularmovies.repo.impl.MovieDetailsRepositoryImpl
import com.insiderser.popularmovies.repo.impl.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository
}
