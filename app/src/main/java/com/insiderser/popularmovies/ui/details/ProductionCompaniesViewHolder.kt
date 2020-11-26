package com.insiderser.popularmovies.ui.details

import androidx.recyclerview.widget.RecyclerView
import com.insiderser.popularmovies.databinding.ItemProductionCompanyBinding
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.util.loadLogo

class ProductionCompaniesViewHolder(
    private val binding: ItemProductionCompanyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productionCompany: ProductionCompany?) {
        binding.image.loadLogo(productionCompany?.logoPath)
    }
}
