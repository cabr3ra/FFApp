package com.oriol.ffapp.rvFruitShop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.FruitShop

class FruitShopRvAdapter(private var fruitShops:List<FruitShop>)
    : RecyclerView.Adapter<FruitShopViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitShopViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return FruitShopViewHolder(layoutInflate.inflate(R.layout.rv_fruit_shop_layout, parent, false))
    }

    override fun onBindViewHolder(holder: FruitShopViewHolder, position: Int) {
        holder.printFruit(fruitShops[position])
    }

    override fun getItemCount(): Int {
        return fruitShops.size
    }

    // Function to update the list of fruit shops
    fun updateFruitShops(newFruitShops: List<FruitShop>) {
        fruitShops = newFruitShops
        notifyDataSetChanged()
    }
}