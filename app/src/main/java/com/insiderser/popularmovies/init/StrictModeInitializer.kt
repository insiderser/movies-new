package com.insiderser.popularmovies.init

import android.content.Context
import android.os.StrictMode
import androidx.startup.Initializer
import com.insiderser.popularmovies.BuildConfig

class StrictModeInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
