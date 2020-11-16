package com.insiderser.popularmovies.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.insiderser.popularmovies.databinding.ItemProductionCompanyBinding
import com.insiderser.popularmovies.model.ProductionCompany
import com.insiderser.popularmovies.model.ProductionCompanyDiffCallback

class ProductionCompaniesAdapter :
    ListAdapter<ProductionCompany, ProductionCompaniesViewHolder>(ProductionCompanyDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductionCompaniesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductionCompanyBinding.inflate(inflater, parent, false)
        return ProductionCompaniesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductionCompaniesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}
