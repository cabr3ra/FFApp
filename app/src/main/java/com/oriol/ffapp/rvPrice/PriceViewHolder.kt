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
        itemView.apply {
            tvFruitName.text = price.fruitName
            tvFruitShopName.text = price.fruitShopName
            tvFruitPrice.text = price.fruitPrice.toString()

            // Obtener el recurso de imagen correspondiente al nombre de la fruta
            val fruitImageResource = getFruitImageResource(price.fruitName)
            fruitImageResource?.let {
                // Si se encuentra el recurso se carga en el ImageView usando Glide
                Glide.with(this).load(it).into(ivFruitImage)
            } ?: run {
                // Si no se encuentra se establecer una imagen predeterminada
                ivFruitImage.setImageResource(R.drawable.frutas)
            }
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



