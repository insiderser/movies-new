package com.insiderser.popularmovies.repo.impl

import androidx.room.withTransaction
import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.dao.GenresDao
import com.insiderser.popularmovies.db.dao.MoviesDao
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.db.entity.MovieGenresEntity
import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.mapper.toGenreEntity
import com.insiderser.popularmovies.mapper.toMovieEntity
import com.insiderser.popularmovies.mapper.toProductionCompanyEntity
import com.insiderser.popularmovies.model.MovieDetails
import com.insiderser.popularmovies.repo.MovieDetailsRepository
import com.insiderser.popularmovies.rest.tmdb.MoviesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val moviesDao: MoviesDao,
    private val genresDao: GenresDao,
    private val productionCompaniesDao: ProductionCompaniesDao,
    private val moviesService: MoviesService
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int): Flow<MovieDetails> {
        val movieFlow = moviesDao.getMovieById(movieId)
        val genresFlow = genresDao.findGenresByMovieId(movieId)
        val productionCompaniesFlow =
            productionCompaniesDao.findProductionCompaniesByMovieId(movieId)

        return combine(
            movieFlow,
            genresFlow,
            productionCompaniesFlow
        ) { movie, genres, productionCompanies ->
            MovieDetails(movie, genres, productionCompanies)
        }
    }

    override suspend fun loadMovieDetails(movieId: Int) {
        val movieDetails = moviesService.getMovieDetails(movieId)

        val movieEntity = movieDetails.toMovieEntity()
        val genreEntities = movieDetails.genres.map { it.toGenreEntity() }
        val productionCompanyEntities = movieDetails.production_companies.map { it.toProductionCompanyEntity() }

        val movieGenreEntities = genreEntities.map {
            MovieGenresEntity(movieId = movieDetails.id, genreId = it.id)
        }
        val movieProductionCompanyEntities = productionCompanyEntities.map {
            MovieProductionCompanyEntity(movieId = movieDetails.id, productionCompanyId = it.id)
        }

        db.withTransaction {
            moviesDao.insert(movieEntity)
            genresDao.insertAll(genreEntities)
            genresDao.insertAllMovieGenres(movieGenreEntities)
            productionCompaniesDao.insertAll(productionCompanyEntities)
            productionCompaniesDao.insertAllMovieProductionCompanies(movieProductionCompanyEntities)
        }
    }
}
