package com.insiderser.popularmovies.util

import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.N)
fun LocaleList.toKotlinList(): List<Locale> = List(size()) { i -> this[i] }
