package com.insiderser.popularmovies.util

import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.insiderser.popularmovies.BuildConfig

fun ImageView.loadPoster(
    path: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadImage(
    path,
    sizePathProvider = { getPosterSizePath(width) },
    imageLoader,
    builder
)

fun ImageView.loadBackdrop(
    path: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadImage(
    path,
    sizePathProvider = { getBackdropSizePath(width) },
    imageLoader,
    builder
)

fun ImageView.loadLogo(
    path: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadImage(
    path,
    sizePathProvider = { getLogoSizePath(width) },
    imageLoader,
    builder
)

fun ImageView.loadProfile(
    path: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadImage(
    path,
    sizePathProvider = { getProfileSizePath(width = width, height = height) },
    imageLoader,
    builder
)

fun ImageView.loadStill(
    path: String?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) = loadImage(
    path,
    sizePathProvider = { getStillSizePath(width) },
    imageLoader,
    builder
)

private fun ImageView.loadImage(
    imagePath: String?,
    sizePathProvider: ImageView.() -> String,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    if (!isLaidOut) {
        doOnPreDraw {
            loadImage(imagePath, sizePathProvider, imageLoader, builder)
        }
        return
    }

    val fullPath = buildPathOrNull(
        sizePath = sizePathProvider(),
        imagePath = imagePath
    )
    load(fullPath, imageLoader, builder)
}

private fun buildPathOrNull(sizePath: String, imagePath: String?): String? {
    return if (imagePath != null) {
        BuildConfig.TMDB_BASE_IMAGES_URL + sizePath + imagePath
    } else {
        null
    }
}

private const val ORIGINAL = "original"

private val posterSizes = listOf(
    1..92 to "w92",
    93..154 to "w154",
    155..185 to "w185",
    186..342 to "w342",
    343..500 to "w500",
    501..780 to "w780"
)

private val backdropSizes = listOf(
    1..300 to "w300",
    301..780 to "w780",
    781..1280 to "w1280"
)

private val logoSizes = listOf(
    1..45 to "w45",
    46..92 to "w92",
    93..154 to "w154",
    155..185 to "w185",
    186..300 to "w300",
    301..500 to "w500"
)

private val profileSizes = listOf(
    1..45 to "w45",
    46..185 to "w185"
)

private val stillSizes = listOf(
    1..92 to "w92",
    93..185 to "w185",
    186..300 to "w300"
)

private fun getPosterSizePath(width: Int) = findSizePath(posterSizes, width) ?: ORIGINAL

private fun getBackdropSizePath(width: Int) = findSizePath(backdropSizes, width) ?: ORIGINAL

private fun getLogoSizePath(width: Int) = findSizePath(logoSizes, width) ?: ORIGINAL

private fun getProfileSizePath(width: Int, height: Int) = findSizePath(profileSizes, width) ?: run {
    if (height <= 632) {
        "h632"
    } else {
        ORIGINAL
    }
}

private fun getStillSizePath(width: Int) = findSizePath(stillSizes, width) ?: ORIGINAL

private fun findSizePath(sizes: List<Pair<IntRange, String>>, width: Int) =
    sizes.find { (widthRange, _) -> width in widthRange }?.second
