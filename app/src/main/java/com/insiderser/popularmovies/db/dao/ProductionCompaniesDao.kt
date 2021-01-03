package com.insiderser.popularmovies.db.dao

import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.model.ProductionCompany
import kotlinx.coroutines.flow.Flow

interface ProductionCompaniesDao {

    fun findProductionCompaniesByMovieId(movieId: Int): Flow<List<ProductionCompany>>
    suspend fun insertAll(productionCompanies: List<ProductionCompanyEntity>)
    suspend fun insertAllMovieProductionCompanies(productionCompanies: List<MovieProductionCompanyEntity>)
}
