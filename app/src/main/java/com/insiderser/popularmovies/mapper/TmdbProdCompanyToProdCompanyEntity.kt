package com.insiderser.popularmovies.mapper

import com.insiderser.popularmovies.db.entity.ProductionCompanyEntity
import com.insiderser.popularmovies.rest.tmdb.model.TmdbProductionCompany

fun TmdbProductionCompany.toProductionCompanyEntity() = ProductionCompanyEntity(
    id = id,
    name = name,
    logoPath = logo_path
)