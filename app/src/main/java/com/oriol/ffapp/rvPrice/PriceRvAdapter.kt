package com.oriol.ffapp.rvPrice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price

class PriceRvAdapter(private var prices: List<Price>) : RecyclerView.Adapter<PriceViewHolder>() {

    fun setData(prices: List<Price>) {
        this.prices = prices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_price_layout, parent, false)
        return PriceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {
        holder.bind(prices[position])
    }

    override fun getItemCount(): Int {
        return prices.size
    }
}

