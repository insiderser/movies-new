package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails
import com.insiderser.popularmovies.rest.tmdb.model.TmdbPopularMovies

fun movieMapper(movie: MovieEntity, genres: List<Genre>, isFavorite: Boolean) = Movie(
    id = movie.id,
    title = movie.title,
    overview = movie.overview,
    posterPath = movie.posterPath,
    backdropPath = movie.backdropPath,
    voteAverage = movie.voteAverage,
    isFavorite = isFavorite,
    genres = genres,
)

val tmdbMoviesToMovieEntitiesMapper = Mapper.build<TmdbPopularMovies, List<MovieEntity>> {
    results.map(tmdbMovieToMovieEntityMapper)
}

val tmdbMovieToMovieEntityMapper = Mapper.build<TmdbMovie, MovieEntity> {
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        voteAverage = vote_average
    )
}

val tmdbDetailsToMovieEntityMapper = Mapper.build<TmdbMovieDetails, MovieEntity> {
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        voteAverage = vote_average
    )
}

fun popularListEntityMapper(startPosition: Int) = Mapper.build<List<MovieEntity>, List<PopularMoviesListEntity>> {
    mapIndexed { index, movieEntity ->
        PopularMoviesListEntity(
            position = startPosition + index,
            movieId = movieEntity.id
        )
    }
}

val movieBasicInfoMapper = Mapper.build<MovieEntity, MovieBasicInfo> {
    MovieBasicInfo(
        id = id,
        title = title,
        posterPath = posterPath,
    )
}
