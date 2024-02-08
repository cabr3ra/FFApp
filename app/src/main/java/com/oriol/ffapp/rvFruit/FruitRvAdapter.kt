package com.oriol.ffapp.rvFruit

import com.oriol.ffapp.model.Fruit
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R

class FruitRvAdapter(private val fruits:List<Fruit>)
    : RecyclerView.Adapter<FruitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return FruitViewHolder(layoutInflate.inflate(R.layout.rv_fruit_layout, parent, false))
    }

    override fun onBindViewHolder(holder: FruitViewHolder, position: Int) {
        holder.printFruit(fruits[position])
    }

    override fun getItemCount(): Int {
        return fruits.size
    }
}