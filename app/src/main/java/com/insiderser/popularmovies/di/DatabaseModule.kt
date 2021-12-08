package com.insiderser.popularmovies.di

import android.content.Context
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.FavoriteMoviesDao
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.db.dao.SimilarMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.create(context)

    @Provides
    @Singleton
    fun provideMoviesDao(db: AppDatabase): MoviesDao = db.moviesDao

    @Provides
    @Singleton
    fun providePopularMoviesListDao(db: AppDatabase): PopularMoviesListDao =
        db.popularMoviesListDao

    @Provides
    @Singleton
    fun provideGenresDao(db: AppDatabase): GenresDao = db.genresDao

    @Provides
    @Singleton
    fun provideReviewsDao(db: AppDatabase): ReviewsDao = db.reviewsDao

    @Provides
    @Singleton
    fun provideFavoriteMoviesDao(db: AppDatabase): FavoriteMoviesDao = db.favoriteMoviesDao

    @Provides
    @Singleton
    fun provideSimilarMoviesDao(db: AppDatabase): SimilarMoviesDao = db.similarMoviesDao
}
