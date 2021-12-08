package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.db.entity.SimilarMoviesEntity
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MovieBasicInfo
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovies

fun movieMapper(
    movie: MovieEntity,
    genres: List<Genre>,
    isFavorite: Boolean,
    similarMovies: List<MovieEntity>,
) = Movie(
    id = movie.id,
    title = movie.title,
    overview = movie.overview,
    posterPath = movie.posterPath,
    backdropPath = movie.backdropPath,
    voteAverage = movie.voteAverage,
    isFavorite = isFavorite,
    genres = genres,
    similar = similarMovies.map(movieBasicInfoMapper),
)

val tmdbMoviesToMovieEntitiesMapper = Mapper.build<TmdbMovies, List<MovieEntity>> {
    results.mapNotNull(tmdbMovieToMovieEntityMapper)
}

val tmdbMovieToMovieEntityMapper = Mapper.build<TmdbMovie, MovieEntity?> {
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path ?: return@build null,
        backdropPath = backdrop_path,
        voteAverage = vote_average
    )
}

val tmdbDetailsToMovieEntityMapper = Mapper.build<TmdbMovieDetails, MovieEntity?> {
    MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path ?: return@build null,
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

val tmdbMoviesToBasicInfoMapper = Mapper.build<TmdbMovies, List<MovieBasicInfo>> {
    this.results.mapNotNull(tmdbMovieToBasicInfoMapper)
}

val tmdbMovieToBasicInfoMapper = Mapper.build<TmdbMovie, MovieBasicInfo?> {
    MovieBasicInfo(
        id = id,
        title = title,
        posterPath = poster_path ?: return@build null,
    )
}

fun similarMoviesMapper(parentMovieId: Int): Mapper<List<MovieEntity>, List<SimilarMoviesEntity>> {
    return Mapper.build {
        mapIndexed { index, movieEntity ->
            SimilarMoviesEntity(
                parentMovieId = parentMovieId,
                similarMovieId = movieEntity.id,
                position = index,
            )
        }
    }
}
