package com.insiderser.popularmovies.util

import android.util.IntProperty
import android.view.View
import androidx.core.view.updateLayoutParams

object ViewAnimationProperties {

    val HEIGHT = object : IntProperty<View>("height") {
        override fun get(obj: View): Int = obj.height

        override fun setValue(obj: View, value: Int) = obj.updateLayoutParams {
            height = value
        }
    }
}
