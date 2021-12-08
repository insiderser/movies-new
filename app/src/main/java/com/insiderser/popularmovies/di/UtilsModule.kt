package com.insiderser.popularmovies.di

import com.insiderser.popularmovies.util.InputValidator
import com.insiderser.popularmovies.util.InputValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
interface UtilsModule {

    @Binds
    fun bindInputValidator(impl: InputValidatorImpl): InputValidator
}
