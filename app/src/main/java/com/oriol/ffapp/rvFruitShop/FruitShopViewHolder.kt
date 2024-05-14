package com.oriol.ffapp.rvFruitShop

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.FruitShop

class FruitShopViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_fruitShopName = view.findViewById<TextView>(R.id.tv_rv_fruitShopName)
    val tv_rv_fruitShopLocation = view.findViewById<TextView>(R.id.tv_rv_fruitShopLocation)
    val tv_rv_fruitShopPhone = view.findViewById<TextView>(R.id.tv_rv_fruitShopPhone)

    fun printFruit(fruit: FruitShop){
        tv_rv_fruitShopName.text = fruit.nameFruitShop
        tv_rv_fruitShopLocation.text = fruit.locationFruitShop
        tv_rv_fruitShopPhone.text = fruit.phoneFruitShop
    }
}