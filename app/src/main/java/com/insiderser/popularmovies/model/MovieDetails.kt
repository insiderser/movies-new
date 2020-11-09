package com.insiderser.popularmovies.model

data class MovieDetails(
    val movie: Movie,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>
)
