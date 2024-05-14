package com.oriol.ffapp.rvPrice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price

class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvFruitName: TextView = itemView.findViewById(R.id.tv_rv_fruitName)
    private val tvFruitShopName: TextView = itemView.findViewById(R.id.tv_rv_fruitShopName)
    private val tvFruitPrice: TextView = itemView.findViewById(R.id.tv_rv_fruitPrice)

    fun bind(price: Price) {
        tvFruitName.text = price.fruitName
        tvFruitShopName.text = price.fruitShopName
        tvFruitPrice.text = price.price.toString()
    }
}



