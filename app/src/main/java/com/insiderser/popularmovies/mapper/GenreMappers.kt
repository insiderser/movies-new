package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbGenre

val tmdbToGenreEntityMapper = Mapper.build<TmdbGenre, GenreEntity> {
    GenreEntity(
        id = id,
        name = name
    )
}

fun movieGenreMapper(movieId: Int) = Mapper.build<GenreEntity, MovieGenresEntity> {
    MovieGenresEntity(movieId = movieId, genreId = id)
}
