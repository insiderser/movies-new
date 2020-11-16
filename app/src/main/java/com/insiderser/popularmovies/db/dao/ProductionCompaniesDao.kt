package com.insiderser.popularmovies.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.model.ProductionCompany
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductionCompaniesDao {

    @Query(
        """
        SELECT productionCompanies.*
        FROM productionCompanies
                 JOIN movieProductionCompanies MPC ON productionCompanies.id = MPC.productionCompanyId
        WHERE movieId = :movieId AND logoPath IS NOT NULL
    """
    )
    fun findProductionCompaniesByMovieId(movieId: Int): Flow<List<ProductionCompany>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productionCompanies: List<ProductionCompanyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovieProductionCompanies(productionCompanies: List<MovieProductionCompanyEntity>)
}
