package com.oriol.ffapp.rvPrice

import com.oriol.ffapp.model.Price
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R

class PriceRvAdapter(private val prices:List<Price>)
    : RecyclerView.Adapter<PriceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return PriceViewHolder(layoutInflate.inflate(R.layout.rv_price_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.printPrice(prices[position])
    }

    override fun getItemCount(): Int {
        return prices.size
    }
}