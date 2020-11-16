package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.GenreEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbGenre

fun TmdbGenre.toGenreEntity() = GenreEntity(
    id = id,
    name = name
)
