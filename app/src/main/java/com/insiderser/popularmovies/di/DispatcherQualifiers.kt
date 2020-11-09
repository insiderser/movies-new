package com.insiderser.popularmovies.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class MainDispatcher

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@MustBeDocumented
@Retention(RUNTIME)
annotation class IODispatcher
