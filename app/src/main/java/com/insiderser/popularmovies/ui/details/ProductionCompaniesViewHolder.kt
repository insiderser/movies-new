package com.insiderser.popularmovies.ui.details

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.insiderser.popularmovies.databinding.ItemProductionCompanyBinding
import com.insiderser.popularmovies.model.ProductionCompany

class ProductionCompaniesViewHolder(
    private val binding: ItemProductionCompanyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productionCompany: ProductionCompany?) {
        binding.image.load(productionCompany?.logoPath)
    }
}
