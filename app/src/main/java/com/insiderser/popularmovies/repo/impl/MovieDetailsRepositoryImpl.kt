package com.insiderser.popularmovies.repo.impl

import androidx.room.withTransaction
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.mapper.movieGenreMapper
import com.insiderser.popularmovies.mapper.movieMapper
import com.insiderser.popularmovies.mapper.movieProductionCompanyMapper
import com.insiderser.popularmovies.mapper.tmdbDetailsToMovieEntityMapper
import com.insiderser.popularmovies.mapper.tmdbToGenreEntityMapper
import com.insiderser.popularmovies.mapper.tmdbToProductionCompanyEntityMapper
import com.insiderser.popularmovies.model.Movie
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@Reusable
class MovieDetailsRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val moviesDao: MoviesDao,
    private val genresDao: GenresDao,
    private val productionCompaniesDao: ProductionCompaniesDao,
    private val moviesService: MoviesService
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int): Flow<Movie> {
        val movieFlow = moviesDao.getMovieById(movieId)
        val genresFlow = genresDao.findGenresByMovieId(movieId)
        val productionCompaniesFlow = productionCompaniesDao.findProductionCompaniesByMovieId(movieId)

        return combine(
            movieFlow,
            genresFlow,
            productionCompaniesFlow,
            ::movieMapper
        )
    }

    override suspend fun loadMovieDetails(movieId: Int) {
        val movieDetails = moviesService.getMovieDetails(movieId)

        val movieEntity = tmdbDetailsToMovieEntityMapper(movieDetails)
        val genreEntities = movieDetails.genres.map(tmdbToGenreEntityMapper)
        val productionCompanyEntities = movieDetails.production_companies.map(tmdbToProductionCompanyEntityMapper)

        val movieGenreEntities = genreEntities.map(movieGenreMapper(movieId))
        val movieProductionCompanyEntities = productionCompanyEntities.map(movieProductionCompanyMapper(movieId))

        db.withTransaction {
            moviesDao.insert(movieEntity)
            genresDao.insertAll(genreEntities)
            genresDao.insertAllMovieGenres(movieGenreEntities)
            productionCompaniesDao.insertAll(productionCompanyEntities)
            productionCompaniesDao.insertAllMovieProductionCompanies(movieProductionCompanyEntities)
        }
    }
}
