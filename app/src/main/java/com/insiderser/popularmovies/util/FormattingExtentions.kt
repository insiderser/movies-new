package com.insiderser.popularmovies.util

import java.text.NumberFormat

fun Double.format(): String = NumberFormat.getNumberInstance().format(this)
fun Long.format(): String = NumberFormat.getIntegerInstance().format(this)
