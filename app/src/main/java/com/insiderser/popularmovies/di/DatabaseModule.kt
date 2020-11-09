package com.insiderser.popularmovies.di

import android.content.Context
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.db.dao.ReviewsDao
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
    fun provideMoviesDao(db: AppDatabase): MoviesDao = db.getMoviesDao()

    @Provides
    @Singleton
    fun providePopularMoviesListDao(db: AppDatabase): PopularMoviesListDao =
        db.getPopularMoviesListDao()

    @Provides
    @Singleton
    fun provideGenresDao(db: AppDatabase): GenresDao = db.getGenresDao()

    @Provides
    @Singleton
    fun provideProductionCompaniesDao(db: AppDatabase): ProductionCompaniesDao =
        db.getProductionCompaniesDao()

    @Provides
    @Singleton
    fun provideReviewsDao(db: AppDatabase): ReviewsDao = db.getReviewsDao()
}
