package com.insiderser.popularmovies.init

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger
import com.insiderser.popularmovies.BuildConfig

class CoilInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val imageLoader = ImageLoader.Builder(context)
            // App crashes on Android O+ devices because shared element transitions are not hardware-accelerated.
            .allowHardware(false)
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger(level = Log.INFO))
                }
            }
            .build()
        Coil.setImageLoader(imageLoader)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
