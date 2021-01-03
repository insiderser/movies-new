package com.insiderser.popularmovies.util

import android.database.Cursor
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull

fun Cursor.getInt(columnName: String) = getInt(getColumnIndex(columnName))
fun Cursor.getIntOrNull(columnName: String) = getIntOrNull(getColumnIndex(columnName))
fun Cursor.getString(columnName: String): String = getString(getColumnIndex(columnName))
fun Cursor.getStringOrNull(columnName: String) = getStringOrNull(getColumnIndex(columnName))
fun Cursor.getFloat(columnName: String) = getFloat(getColumnIndex(columnName))
