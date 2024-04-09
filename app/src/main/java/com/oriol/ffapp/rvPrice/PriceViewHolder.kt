package com.oriol.ffapp.rvPrice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price

class PriceViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_fruitName = view.findViewById<TextView>(R.id.tv_rv_fruitName)
    val tv_rv_fruitShopName = view.findViewById<TextView>(R.id.tv_rv_fruitShopName)
    val tv_rv_fruitPrice = view.findViewById<TextView>(R.id.tv_rv_fruitPrice)

    fun printPrice(price: Price){
        tv_rv_fruitName.text = price.fruitName
        tv_rv_fruitShopName.text = price.fruitShopName
        tv_rv_fruitPrice.text = price.fruitPrice
    }
}