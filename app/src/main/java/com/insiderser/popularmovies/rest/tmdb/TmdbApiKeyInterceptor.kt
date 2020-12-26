package com.insiderser.popularmovies.rest.tmdb

import com.insiderser.popularmovies.BuildConfig
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@Reusable
class TmdbApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val url = initialRequest.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .build()
        val newRequest = initialRequest.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}
