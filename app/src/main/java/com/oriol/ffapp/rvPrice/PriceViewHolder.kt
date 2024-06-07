package com.oriol.ffapp.rvPrice

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price

class PriceViewHolder(itemView: View, private val onItemClicked: (Price) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val tvFruitName: TextView = itemView.findViewById(R.id.tv_rv_fruitName)
    private val tvFruitShopName: TextView = itemView.findViewById(R.id.tv_rv_fruitShopName)
    private val tvFruitPrice: TextView = itemView.findViewById(R.id.tv_rv_fruitPrice)
    private val ivFruitImage: ImageView = itemView.findViewById(R.id.iv_rv_fruitImage)

    fun bind(price: Price) {
        tvFruitName.text = price.fruitName
        tvFruitShopName.text = price.fruitShopName
        tvFruitPrice.text = "${price.fruitPrice} €/Kg"

        val fruitImageResource = getFruitImageResource(price.fruitName)
        fruitImageResource?.let {
            Glide.with(itemView.context).load(it).into(ivFruitImage)
        } ?: run {
            ivFruitImage.setImageResource(R.drawable.frutas)
        }

        itemView.setOnClickListener {
            onItemClicked(price)
        }
    }

    private fun getFruitImageResource(fruitName: String): Int? {
        return when (fruitName) {
            "Aguacate" -> R.drawable.aguacate
            "Albaricoque" -> R.drawable.albaricoque
            "Cereza" -> R.drawable.cereza
            "Ciruela" -> R.drawable.ciruela
            "Frambuesa" -> R.drawable.frambuesa
            "Fresa" -> R.drawable.fresa
            "Kiwi" -> R.drawable.kiwi
            "Mandarina" -> R.drawable.mandarina
            "Manzana" -> R.drawable.manzana
            "Melocotón" -> R.drawable.melocoton
            "Melón" -> R.drawable.melon
            "Mora" -> R.drawable.mora
            "Naranja" -> R.drawable.naranja
            "Pera" -> R.drawable.pera
            "Plátano" -> R.drawable.platano
            "Sandía" -> R.drawable.sandia
            "Tomate" -> R.drawable.tomate
            "Uva" -> R.drawable.uva
            else -> null
        }
    }
}
