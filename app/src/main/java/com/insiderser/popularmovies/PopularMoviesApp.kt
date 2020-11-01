package com.insiderser.popularmovies

import android.app.Application
import com.insiderser.popularmovies.di.AppComponent
import com.insiderser.popularmovies.di.ContextModule
import com.insiderser.popularmovies.di.DaggerAppComponent

class PopularMoviesApp : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        @Volatile
        private lateinit var instance: PopularMoviesApp

        fun get() = instance
    }
}
