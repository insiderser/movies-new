package com.insiderser.popularmovies.rest.tmdb

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

class TmdbLocaleInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val locale = context.resources.configuration.locales.get(0) ?: Locale.getDefault()

        val url = initialRequest.url.newBuilder()
            .addQueryParameter(PARAM_LANGUAGE, locale.language)
            .addQueryParameter(PARAM_REGION, locale.country)
            .build()

        val newRequest = initialRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val PARAM_LANGUAGE = "language"
        private const val PARAM_REGION = "region"
    }
}
