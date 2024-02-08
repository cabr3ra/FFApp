package com.oriol.ffapp.model

class FruitProvider {
    companion object {
        val fruits = mutableListOf<Fruit>(
            Fruit("Tomate", 5.0, "Tomate Cherry", "Fruta Sant Antoni"),
            Fruit("Manzana", 10.0, "Manzana Fuji", "Fruta Sant Antoni"),
            )
    }
}
