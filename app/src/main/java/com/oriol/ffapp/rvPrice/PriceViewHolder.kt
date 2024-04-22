package com.oriol.ffapp.rvPrice

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price

class PriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val tvFruitName: TextView = itemView.findViewById(R.id.tv_rv_fruitName)
    private val tvFruitShopName: TextView = itemView.findViewById(R.id.tv_rv_fruitShopName)
    private val tvFruitPrice: TextView = itemView.findViewById(R.id.tv_rv_fruitPrice)
    private val ivFruitImage: ImageView = itemView.findViewById(R.id.iv_rv_fruitImage)

    fun bind(price: Price) {
        tvFruitName.text = price.fruitName
        tvFruitShopName.text = price.fruitShopName
        tvFruitPrice.text = price.fruitPrice

        // Obtener el nombre de la imagen
        val fruitName = price.fruitName ?: ""
        val imageName = "${fruitName.toLowerCase()}.png"

        // Obtener el ID del recurso de la imagen
        val resourceId = itemView.resources.getIdentifier(imageName, "drawable", itemView.context.packageName)

        // Cargar la imagen utilizando Glide
        Glide.with(itemView)
            .load(resourceId)
            .into(ivFruitImage)
    }
}
