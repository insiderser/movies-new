package com.insiderser.popularmovies.di

import com.insiderser.popularmovies.repo.FavoriteMoviesRepository
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.repo.MoviesRepository
import com.insiderser.popularmovies.repo.ReviewsRepository
import com.insiderser.popularmovies.repo.SearchRepository
import com.insiderser.popularmovies.repo.impl.FavoriteMoviesRepositoryImpl
import com.insiderser.popularmovies.repo.impl.MovieDetailsRepositoryImpl
import com.insiderser.popularmovies.repo.impl.MoviesRepositoryImpl
import com.insiderser.popularmovies.repo.impl.ReviewsRepositoryImpl
import com.insiderser.popularmovies.repo.impl.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface RepoModule {

    @Binds
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    fun bindMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository

    @Binds
    fun bindReviewsRepository(impl: ReviewsRepositoryImpl): ReviewsRepository

    @Binds
    fun bindFavoriteMoviesRepository(impl: FavoriteMoviesRepositoryImpl): FavoriteMoviesRepository

    @Binds
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
}
