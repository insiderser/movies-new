package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.MovieProductionCompanyEntity
import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbProductionCompany

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
