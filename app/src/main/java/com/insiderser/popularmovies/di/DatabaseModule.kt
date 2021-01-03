package com.insiderser.popularmovies.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.AppDatabaseImpl
import com.insiderser.popularmovies.db.DbHelperFactory
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.db.dao.impl.GenresDaoImpl
import com.insiderser.popularmovies.db.dao.impl.MoviesDaoImpl
import com.insiderser.popularmovies.db.dao.impl.PopularMoviesListDaoImpl
import com.insiderser.popularmovies.db.dao.impl.ProductionCompaniesDaoImpl
import com.insiderser.popularmovies.db.dao.impl.ReviewsDaoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    @Binds
    @Singleton
    fun bindAppDatabase(impl: AppDatabaseImpl): AppDatabase

    @Binds
    @Singleton
    fun bindMoviesDao(impl: MoviesDaoImpl): MoviesDao

    @Binds
    @Singleton
    fun bindPopularMoviesListDao(impl: PopularMoviesListDaoImpl): PopularMoviesListDao

    @Binds
    @Singleton
    fun bindGenresDao(impl: GenresDaoImpl): GenresDao

    @Binds
    @Singleton
    fun bindProductionCompaniesDao(impl: ProductionCompaniesDaoImpl): ProductionCompaniesDao

    @Binds
    @Singleton
    fun bindReviewsDao(impl: ReviewsDaoImpl): ReviewsDao

    companion object {
        @Provides
        @Singleton
        fun provideDbHelper(factory: DbHelperFactory): SupportSQLiteOpenHelper = factory.create()
    }
}
