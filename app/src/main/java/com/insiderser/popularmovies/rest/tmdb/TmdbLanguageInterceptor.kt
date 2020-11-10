package com.insiderser.popularmovies.rest.tmdb

import android.content.Context
import android.os.Build
import com.insiderser.popularmovies.di.Tmdb
import com.insiderser.popularmovies.util.toKotlinList
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Tmdb
class TmdbLanguageInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    companion object {
        private const val FALLBACK_LANGUAGE = "en-US"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val url = initialRequest.url.newBuilder()
            .addQueryParameter("language", getUserLanguage())
            .build()
        val newRequest = initialRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }

    @Suppress("DEPRECATION")
    private fun getUserLanguage(): String {
        // TODO: extract this to a separate domain class.
        val configuration = context.resources.configuration
        val acceptableUserLanguages = getAcceptableUserLanguages()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.toKotlinList()
                .find { it.language in acceptableUserLanguages }
                ?.language
        } else {
            configuration.locale.language.takeIf { it in acceptableUserLanguages }
        } ?: FALLBACK_LANGUAGE
    }

    // TODO: retrieve from TMDB and store in local cache.
    private fun getAcceptableUserLanguages(): Array<String> = emptyArray()
}
