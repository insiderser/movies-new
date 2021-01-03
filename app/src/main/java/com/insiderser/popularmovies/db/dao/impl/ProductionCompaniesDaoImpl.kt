package com.insiderser.popularmovies.db.dao.impl

import com.insiderser.popularmovies.db.AppDatabase
import com.insiderser.popularmovies.db.DbContract
import com.insiderser.popularmovies.db.InvalidationTracker
import com.insiderser.popularmovies.db.dao.ProductionCompaniesDao
import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.mapper.cursorToProdCompanyMapper
import com.insiderser.popularmovies.mapper.map
import com.insiderser.popularmovies.mapper.movieProdCompanyEntityToContentValuesMapper
import com.insiderser.popularmovies.mapper.prodCompanyEntityToContentValuesMapper
import com.insiderser.popularmovies.model.ProductionCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductionCompaniesDaoImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ProductionCompaniesDao {

    override fun findProductionCompaniesByMovieId(movieId: Int): Flow<List<ProductionCompany>> =
        callbackFlow<List<ProductionCompany>> {
            suspend fun emitItems() {
                val cursor = appDatabase.query(DbContract.ProductionCompanies.QUERY_BY_MOVIE_ID, arrayOf(movieId))
                send(cursor.map(cursorToProdCompanyMapper))
            }

            emitItems()
            val invalidationTracker = InvalidationTracker { emitItems() }

            appDatabase.addInvalidationTracker(invalidationTracker)
            awaitClose { appDatabase.removeInvalidationTracker(invalidationTracker) }
        }
            .flowOn(Dispatchers.IO)

    private suspend fun insert(entity: ProductionCompanyEntity) {
        appDatabase.insert(
            table = DbContract.ProductionCompanies.TABLE_NAME,
            values = prodCompanyEntityToContentValuesMapper(entity),
        )
    }

    private suspend fun insert(entity: MovieProductionCompanyEntity) {
        appDatabase.insert(
            table = DbContract.ProductionCompanies.TABLE_NAME_MOVIE,
            values = movieProdCompanyEntityToContentValuesMapper(entity),
        )
    }

    override suspend fun insertAll(productionCompanies: List<ProductionCompanyEntity>) = appDatabase.inTransaction {
        productionCompanies.forEach { insert(it) }
    }

    override suspend fun insertAllMovieProductionCompanies(productionCompanies: List<MovieProductionCompanyEntity>) =
        appDatabase.inTransaction {
            productionCompanies.forEach { insert(it) }
        }
}
