package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productionCompanies")
data class ProductionCompanyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val logoPath: String? = null
)
