package com.insiderser.popularmovies.db

object DbContract {

    const val DB_VERSION = 1
    const val DB_FILE_NAME = "movies.db"

    object Movies {
        const val TABLE_NAME = "movies"

        const val QUERY_MOVIE_BY_ID =
            // language=RoomSql
            """
            SELECT * FROM movies WHERE id = ?
            """
    }

    object PopularMovies {
        const val TABLE_NAME = "popularMoviesList"

        // language=
        const val COLUMN_VALUE = "value"

        @JvmStatic
        fun getQueryAll(prepend: Boolean = false) =
            // language=RoomSql
            """
            SELECT movies.*
            FROM popularMoviesList
                    JOIN movies ON popularMoviesList.movieId = movies.id
            WHERE popularMoviesList.position ${if (prepend) "<" else ">="} ?
            ORDER BY position ${if (prepend) "DESC" else "ASC"}
            LIMIT ?
            """

        const val QUERY_FIRST_POSITION =
            // language=RoomSql
            """
            SELECT MIN(position) AS $COLUMN_VALUE FROM popularMoviesList
            """

        const val QUERY_LAST_POSITION =
            // language=RoomSql
            """
            SELECT MAX(position) AS $COLUMN_VALUE FROM popularMoviesList
            """
    }

    object Genres {
        const val TABLE_NAME = "genres"
        const val TABLE_NAME_MOVIE = "movieGenres"

        const val QUERY_BY_MOVIE_ID =
            // language=RoomSql
            """
            SELECT genres.id, genres.name
            FROM genres
                     JOIN movieGenres mg ON genres.id = mg.genreId
            WHERE movieId = ?
            """
    }

    object ProductionCompanies {
        const val TABLE_NAME = "productionCompanies"
        const val TABLE_NAME_MOVIE = "movieProductionCompanies"

        const val QUERY_BY_MOVIE_ID =
            // language=RoomSql
            """
            SELECT productionCompanies.*
            FROM productionCompanies
                     JOIN movieProductionCompanies MPC ON productionCompanies.id = MPC.productionCompanyId
            WHERE movieId = ? AND logoPath IS NOT NULL
            """
    }

    object Reviews {
        const val TABLE_NAME = "reviews"

        const val QUERY_BY_MOVIE_ID =
            // language=RoomSql
            """
            SELECT * FROM reviews WHERE movieId = ?
            """
    }
}
