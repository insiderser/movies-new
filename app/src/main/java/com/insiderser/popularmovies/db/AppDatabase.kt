package com.insiderser.popularmovies.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

interface AppDatabase {

    suspend fun query(statement: String, args: Array<Any>? = null): Cursor

    /**
     * @param conflictAlgorithm for insert conflict resolver. One of
     *          [android.database.sqlite.SQLiteDatabase.CONFLICT_NONE], [android.database.sqlite.SQLiteDatabase.CONFLICT_ROLLBACK],
     *          [android.database.sqlite.SQLiteDatabase.CONFLICT_ABORT], [android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL],
     *          [android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE], [android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE].
     */
    suspend fun insert(
        table: String,
        values: ContentValues,
        conflictAlgorithm: Int = SQLiteDatabase.CONFLICT_REPLACE
    ): Long

    suspend fun delete(tableName: String): Int

    fun addInvalidationTracker(tracker: InvalidationTracker)
    fun removeInvalidationTracker(tracker: InvalidationTracker)

    suspend fun <T> inTransaction(action: suspend () -> T): T
}
