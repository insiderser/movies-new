package com.insiderser.popularmovies.util

import android.widget.ImageView
import com.insiderser.popularmovies.BuildConfig

fun ImageView.loadPoster(
    path: String?,
    builder: LoadImageBuilder? = null
) = loadImage(
    path,
    sizePathProvider = { getPosterSizePath(width) },
    builder
)

fun ImageView.loadBackdrop(
    path: String?,
    builder: LoadImageBuilder? = null
) = loadImage(
    path,
    sizePathProvider = { getBackdropSizePath(width) },
    builder
)

fun ImageView.loadLogo(
    path: String?,
    builder: LoadImageBuilder? = null
) = loadImage(
    path,
    sizePathProvider = { getLogoSizePath(width) },
    builder
)

fun ImageView.loadProfile(
    path: String?,
    builder: LoadImageBuilder? = null
) = loadImage(
    path,
    sizePathProvider = { getProfileSizePath(width = width, height = height) },
    builder
)

fun ImageView.loadStill(
    path: String?,
    builder: LoadImageBuilder? = null
) = loadImage(
    path,
    sizePathProvider = { getStillSizePath(width) },
    builder
)

private fun ImageView.loadImage(
    imagePath: String?,
    sizePathProvider: ImageView.() -> String,
    builder: LoadImageBuilder? = null
) {
    val fullPath = buildPathOrNull(
        sizePath = sizePathProvider(),
        imagePath = imagePath
    )
    load(fullPath, builder)
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
