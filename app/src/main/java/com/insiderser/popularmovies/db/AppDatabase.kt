package com.insiderser.popularmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.insiderser.popularmovies.db.converters.OffsetDateTimeConverter
import com.insiderser.popularmovies.db.dao.FavoriteMoviesDao
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.PopularMoviesListDao
import com.insiderser.popularmovies.db.dao.ReviewsDao
import com.insiderser.popularmovies.db.dao.SimilarMoviesDao
import com.insiderser.popularmovies.db.entity.FavoriteMovieEntity
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.db.entity.ReviewsEntity
import com.insiderser.popularmovies.db.entity.SimilarMoviesEntity

private const val DB_VERSION = 1
private const val DB_FILE_NAME = "movies.db"

@Database(
    entities = [
        MovieEntity::class,
        PopularMoviesListEntity::class,
        GenreEntity::class,
        MovieGenresEntity::class,
        ReviewsEntity::class,
        FavoriteMovieEntity::class,
        SimilarMoviesEntity::class,
    ],
    version = DB_VERSION
)
@TypeConverters(
    OffsetDateTimeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val popularMoviesListDao: PopularMoviesListDao
    abstract val genresDao: GenresDao
    abstract val reviewsDao: ReviewsDao
    abstract val favoriteMoviesDao: FavoriteMoviesDao
    abstract val similarMoviesDao: SimilarMoviesDao

    companion object {
        @JvmStatic
        fun create(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DB_FILE_NAME)
                .build()
    }
}
