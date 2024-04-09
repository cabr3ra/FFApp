package com.oriol.ffapp.model

class PriceProvider {
    companion object {
        val price = mutableListOf<Price>(
            Price("Manzana", "Mercado de Sant Antoni", "1.80"),
            Price("Manzana", "Arrels Fruita i Verdura", "1.82"),
            Price("Manzana", "L’hort Supermarket", "1.84"),
            Price("Manzana", "Frutas Esther", "1.90"),
            Price("Naranja", "Mercado de Sant Antoni", "2.00"),
            Price("Naranja", "Arrels Fruita i Verdura", "2.10"),
            Price("Fresa", "L’hort Supermarket", "2.10"),
            Price("Fresa", "Frutas Esther", "2.20")
            )
    }
}
