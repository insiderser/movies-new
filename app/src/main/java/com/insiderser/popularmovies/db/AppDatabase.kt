package com.insiderser.popularmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ReviewsEntity

private const val DB_VERSION = 1
private const val DB_FILE_NAME = "movies.db"

@Database(
    entities = [
        MovieEntity::class,
        PopularMoviesListEntity::class,
        GenreEntity::class,
        MovieGenresEntity::class,
        ProductionCompanyEntity::class,
        MovieProductionCompanyEntity::class,
        ReviewsEntity::class,
    ],
    version = DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao
    abstract fun getPopularMoviesListDao(): PopularMoviesListDao
    abstract fun getGenresDao(): GenresDao
    abstract fun getProductionCompaniesDao(): ProductionCompaniesDao
    abstract fun getReviewsDao(): ReviewsDao

    companion object {
        @JvmStatic
        fun create(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DB_FILE_NAME)
                .build()
    }
}
