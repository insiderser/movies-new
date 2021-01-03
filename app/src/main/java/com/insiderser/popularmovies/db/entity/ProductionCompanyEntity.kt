package com.insiderser.popularmovies.db.entity

data class ProductionCompanyEntity(
    val id: Int,
    val name: String,
    val logoPath: String? = null
)
