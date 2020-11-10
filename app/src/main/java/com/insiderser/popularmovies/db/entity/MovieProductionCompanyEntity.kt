package com.insiderser.popularmovies.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movieProductionCompanies",
    indices = [Index("movieId"), Index("productionCompanyId")],
    primaryKeys = ["movieId", "productionCompanyId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"]
        ),
        ForeignKey(
            entity = ProductionCompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["productionCompanyId"]
        )
    ]
)
data class MovieProductionCompanyEntity(
    val movieId: Int,
    val productionCompanyId: Int
)
