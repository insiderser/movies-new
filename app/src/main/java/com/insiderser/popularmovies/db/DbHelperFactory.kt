package com.insiderser.popularmovies.db

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Scanner
import javax.inject.Inject

class DbHelperFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val callback: Callback,
) {

    fun create(): SupportSQLiteOpenHelper = FrameworkSQLiteOpenHelperFactory().create(
        SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(DbContract.DB_FILE_NAME)
            .callback(callback)
            .build()
    )

    class Callback @Inject constructor(
        @ApplicationContext private val context: Context
    ) : SupportSQLiteOpenHelper.Callback(DbContract.DB_VERSION) {
        override fun onCreate(db: SupportSQLiteDatabase) = executeSqlFromFile("db.sql", db)
        override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) = TODO("not implemented")

        private fun executeSqlFromFile(fileName: String, db: SupportSQLiteDatabase) {
            val inputStream = context.assets.open(fileName)
            Scanner(inputStream).use { scanner ->
                scanner.useDelimiter(";\n")
                scanner.forEach { statement ->
                    db.execSQL(statement)
                }
            }
        }
    }
}
