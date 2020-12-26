package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MoviePoster
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails

fun movieMapper(movie: MovieEntity, genres: List<Genre>, productionCompanies: List<ProductionCompany>) = Movie(
    id = movie.id,
    title = movie.title,
    overview = movie.overview,
    posterPath = movie.posterPath,
    backdropPath = movie.backdropPath,
    voteAverage = movie.voteAverage,
    genres = genres,
    productionCompanies = productionCompanies
)

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

val moviePosterMapper = Mapper.build<MovieEntity, MoviePoster> {
    MoviePoster(
        id = id,
        title = title,
        posterPath = posterPath,
    )
}
