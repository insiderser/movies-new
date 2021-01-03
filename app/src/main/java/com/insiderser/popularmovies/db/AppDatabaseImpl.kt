package com.insiderser.popularmovies.db

import android.content.ContentValues
import android.database.Cursor
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.transaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CopyOnWriteArraySet
import javax.inject.Inject

class AppDatabaseImpl @Inject constructor(
    private val dbHelper: SupportSQLiteOpenHelper,
) : AppDatabase {

    private val invalidationTrackers = CopyOnWriteArraySet<InvalidationTracker>()

    override suspend fun query(statement: String, args: Array<Any>?): Cursor = withContext(Dispatchers.IO) {
        dbHelper.readableDatabase.query(statement, args)
    }

    override suspend fun insert(table: String, values: ContentValues, conflictAlgorithm: Int) = inTransaction {
        dbHelper.writableDatabase.insert(table, conflictAlgorithm, values)
    }

    override suspend fun delete(tableName: String): Int = inTransaction {
        dbHelper.writableDatabase.delete(tableName, null, null)
    }

    override suspend fun <T> inTransaction(action: suspend () -> T) = withContext(Dispatchers.IO) {
        dbHelper.writableDatabase.transaction { action() }.also { maybeNotify() }
    }

    private suspend fun maybeNotify() {
        if (!dbHelper.writableDatabase.inTransaction()) {
            notifyInvalidationTrackers()
        }
    }

    private suspend fun notifyInvalidationTrackers() {
        invalidationTrackers.forEach { it.invoke() }
    }

    override fun addInvalidationTracker(tracker: InvalidationTracker) {
        invalidationTrackers.add(tracker)
    }

    override fun removeInvalidationTracker(tracker: InvalidationTracker) {
        invalidationTrackers.remove(tracker)
    }
}
