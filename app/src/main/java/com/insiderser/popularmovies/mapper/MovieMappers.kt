package com.insiderser.popularmovies.mapper

import android.content.ContentValues
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.entity.MovieEntity
import com.insiderser.popularmovies.db.entity.PopularMoviesListEntity
import com.insiderser.popularmovies.model.Genre
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.model.MoviePoster
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovie
import com.insiderser.popularmovies.rest.tmdb.model.TmdbMovieDetails
import com.insiderser.popularmovies.rest.tmdb.model.TmdbPopularMovies
import com.insiderser.popularmovies.util.getFloat
import com.insiderser.popularmovies.util.getInt
import com.insiderser.popularmovies.util.getIntOrNull
import com.insiderser.popularmovies.util.getString
import com.insiderser.popularmovies.util.getStringOrNull

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

val moviePosterMapper = Mapper.build<MovieEntity, MoviePoster> {
    MoviePoster(
        id = id,
        title = title,
        posterPath = posterPath,
    )
}

val movieEntityToContentValuesMapper = Mapper.build<MovieEntity, ContentValues> {
    contentValuesOf(
        "id" to id,
        "title" to title,
        "overview" to overview,
        "posterPath" to posterPath,
        "backdropPath" to backdropPath,
        "voteAverage" to voteAverage,
    )
}

val popularMovieEntityToContentValuesMapper = Mapper.build<PopularMoviesListEntity, ContentValues> {
    contentValuesOf(
        "position" to position,
        "movieId" to movieId,
    )
}

val cursorToMovieEntityMapper = Mapper.build<Cursor, MovieEntity> {
    MovieEntity(
        id = getInt("id"),
        title = getString("title"),
        overview = getStringOrNull("overview"),
        posterPath = getStringOrNull("posterPath"),
        backdropPath = getStringOrNull("backdropPath"),
        voteAverage = getFloat("voteAverage"),
    )
}

val cursorToIntValueMapper = Mapper.build<Cursor, Int?> {
    getIntOrNull(DbContract.PopularMovies.COLUMN_VALUE)
}
