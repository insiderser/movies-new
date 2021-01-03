package com.insiderser.popularmovies.mapper

import android.content.ContentValues
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.rest.tmdb.model.TmdbGenre
import com.insiderser.popularmovies.util.getInt
import com.insiderser.popularmovies.util.getString

val tmdbToGenreEntityMapper = Mapper.build<TmdbGenre, GenreEntity> {
    GenreEntity(
        id = id,
        name = name
    )
}

fun movieGenreMapper(movieId: Int) = Mapper.build<GenreEntity, MovieGenresEntity> {
    MovieGenresEntity(movieId = movieId, genreId = id)
}

val genreEntityToContentValuesMapper = Mapper.build<GenreEntity, ContentValues> {
    contentValuesOf(
        "id" to id,
        "name" to name,
    )
}

val movieGenreEntityToContentValuesMapper = Mapper.build<MovieGenresEntity, ContentValues> {
    contentValuesOf(
        "genreId" to genreId,
        "movieId" to movieId,
    )
}

val cursorToGenreMapper = Mapper.build<Cursor, Genre> {
    Genre(
        id = getInt("id"),
        name = getString("name"),
    )
}
