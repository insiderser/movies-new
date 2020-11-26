package com.insiderser.popularmovies.model

import androidx.recyclerview.widget.DiffUtil

data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?
)

object ProductionCompanyDiffCallback : DiffUtil.ItemCallback<ProductionCompany>() {
    override fun areItemsTheSame(oldItem: ProductionCompany, newItem: ProductionCompany): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ProductionCompany,
        newItem: ProductionCompany
    ): Boolean = oldItem == newItem
}
