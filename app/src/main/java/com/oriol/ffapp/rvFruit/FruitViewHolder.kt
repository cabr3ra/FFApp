package com.oriol.ffapp.rvFruit

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Fruit

class FruitViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_fruitName = view.findViewById<TextView>(R.id.tv_rv_fruitName)
    val tv_rv_fruitDescription = view.findViewById<TextView>(R.id.tv_rv_fruitDescription)

    fun printFruit(fruit: Fruit){
        tv_rv_fruitName.text = fruit.fruitName
        tv_rv_fruitDescription.text = fruit.fruitDescription
    }
}