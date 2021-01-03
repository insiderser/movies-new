package com.insiderser.popularmovies.mapper

import android.content.ContentValues
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.rest.tmdb.model.TmdbProductionCompany
import com.insiderser.popularmovies.util.getInt
import com.insiderser.popularmovies.util.getString
import com.insiderser.popularmovies.util.getStringOrNull

val tmdbToProductionCompanyEntityMapper = Mapper.build<TmdbProductionCompany, ProductionCompanyEntity> {
    ProductionCompanyEntity(
        id = id,
        name = name,
        logoPath = logo_path
    )
}

fun movieProductionCompanyMapper(movieId: Int) = Mapper.build<ProductionCompanyEntity, MovieProductionCompanyEntity> {
    MovieProductionCompanyEntity(movieId = movieId, productionCompanyId = id)
}

val prodCompanyEntityToContentValuesMapper = Mapper.build<ProductionCompanyEntity, ContentValues> {
    contentValuesOf(
        "id" to id,
        "name" to name,
        "logoPath" to logoPath,
    )
}

val movieProdCompanyEntityToContentValuesMapper = Mapper.build<MovieProductionCompanyEntity, ContentValues> {
    contentValuesOf(
        "productionCompanyId" to productionCompanyId,
        "movieId" to movieId,
    )
}

val cursorToProdCompanyMapper = Mapper.build<Cursor, ProductionCompany> {
    ProductionCompany(
        id = getInt("id"),
        name = getString("name"),
        logoPath = getStringOrNull("logoPath"),
    )
}
