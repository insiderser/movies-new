package com.insiderser.popularmovies.di

import com.insiderser.popularmovies.BuildConfig
import com.insiderser.popularmovies.rest.tmdb.GenresService
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import com.insiderser.popularmovies.rest.tmdb.TmdbApiKeyInterceptor
import com.insiderser.popularmovies.rest.tmdb.TmdbLanguageInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
@MustBeDocumented
annotation class Tmdb

@Module
@InstallIn(SingletonComponent::class)
object RestModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor.Logger { message -> Timber.tag("OkHttp").d(message) }
        return HttpLoggingInterceptor(logger).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Tmdb
    fun provideTmdbOkHttpClient(
        apiKeyInterceptor: TmdbApiKeyInterceptor,
        languageInterceptor: TmdbLanguageInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(languageInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @Tmdb
    @OptIn(ExperimentalSerializationApi::class)
    fun provideTmdbRetrofit(@Tmdb okHttpClient: OkHttpClient): Retrofit {
        val jsonMediaType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_REST_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesService(@Tmdb retrofit: Retrofit): MoviesService = retrofit.create()

    @Provides
    @Singleton
    fun provideGenresService(@Tmdb retrofit: Retrofit): GenresService = retrofit.create()
}
