package com.insiderser.popularmovies.util

import android.content.Context
import androidx.annotation.StringRes
import com.insiderser.popularmovies.R
import com.insiderser.popularmovies.rest.tmdb.model.TmdbErrors
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketException
import javax.net.ssl.SSLException

sealed class PopularMoviesException : RuntimeException() {
    object NoInternet : PopularMoviesException()
    data class MessageId(@StringRes val payloadRes: Int) : PopularMoviesException()
    data class Message(val payload: String) : PopularMoviesException()
    object Unknown : PopularMoviesException()
}

fun PopularMoviesException.getMessageForUser(context: Context): CharSequence = when (this) {
    PopularMoviesException.NoInternet -> context.getText(R.string.error_no_internet)
    is PopularMoviesException.Message -> payload
    is PopularMoviesException.MessageId -> context.getText(payloadRes)
    PopularMoviesException.Unknown -> context.getText(R.string.error_unknown)
}

fun Throwable.toPopularMoviesException(): PopularMoviesException = when (this) {
    !is Exception -> throw this // This is probably developer error. We need to know about this.
    is PopularMoviesException -> this
    is java.net.UnknownHostException, is SocketException, is SSLException -> PopularMoviesException.NoInternet
    is HttpException -> this.toPopularMoviesException()
    else -> PopularMoviesException.Unknown.also { Timber.e(this) }
}

private fun HttpException.toPopularMoviesException(): PopularMoviesException {
    val errors = parseErrorOrNull() ?: return PopularMoviesException.Unknown
    return PopularMoviesException.Message(errors.errors.joinToString())
}

private fun HttpException.parseErrorOrNull(): TmdbErrors? =
    runCatching { response()?.body()?.toString() ?: return null }
        .mapCatching { Json.decodeFromString<TmdbErrors>(it) }
        .getOrNull()
