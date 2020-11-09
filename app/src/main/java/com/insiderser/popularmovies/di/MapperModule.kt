package com.insiderser.popularmovies.di

import com.insiderser.popularmovies.mapper.TmdbMovieToMovieEntityMapper
import com.insiderser.popularmovies.mapper.TmdbMovieToMovieEntityMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Binds
    fun bindTmdbMovieToMovieEntityMapper(impl: TmdbMovieToMovieEntityMapperImpl): TmdbMovieToMovieEntityMapper
}
