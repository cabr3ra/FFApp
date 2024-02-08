package com.oriol.ffapp.rvFruit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Fruit

class FruitViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_fruitname = view.findViewById<TextView>(R.id.tv_rv_fruitName)
    val tv_rv_price = view.findViewById<TextView>(R.id.tv_rv_price)
    val tv_rv_description = view.findViewById<TextView>(R.id.tv_rv_description)
    val tv_rv_fruitshop = view.findViewById<TextView>(R.id.tv_rv_fruitshop)

    fun printFruit(fruit: Fruit){
        tv_rv_fruitname.text = fruit.fruitName
        tv_rv_price.text = fruit.price.toString()
        tv_rv_description.text = fruit.description
        tv_rv_fruitshop.text = fruit.fruitShop
    }
}