package com.oriol.ffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oriol.ffapp.model.FruitShop

class Maps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        createFragment()
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarkers()
    }

    private fun createMarkers() {
        val fruitShops = listOf(
            FruitShop("Mercado de Sant Antoni", "Carrer del Comte d'Urgell, 1, Barcelona", "934263521"),
            FruitShop("Arrels Fruita i Verdura", "Avinguda de Mistral, 24, Barcelona", "936787010"),
            FruitShop("L’hort Supermarket", "Avinguda de Mistral, 39, Barcelona", "935275097"),
            FruitShop("Frutas Esther", "Carrer de Vilamarí, 35, Barcelona", "933250579"),
            FruitShop("Fruit Sa2pe", "Carrer de Calàbria, 17, Barcelona", "932892088"),
            FruitShop("Frutes I Verdures", "Carrer de Manso, 60, Barcelona", "632196896"),
            FruitShop("Fruiteries Borau", "Carrer de Sepúlveda, 130, Barcelona", "934250125"),
            FruitShop("Fruites Ureña", "Carrer del Parlament, 39, Barcelona", "936115578")
        )

        val coordinates = listOf(
            LatLng(41.37876867939558, 2.1620253391607633),
            LatLng(41.37755806722884, 2.1573118914668306),
            LatLng(41.37655203953501, 2.1565360814893864),
            LatLng(41.37661078420362, 2.1521527256675155),
            LatLng(41.37592913653946, 2.1597832121743927),
            LatLng(41.37755630130479, 2.1619233588271682),
            LatLng(41.38069977464263, 2.159207596832107),
            LatLng(41.37700993399744, 2.1630170851881987)
        )

        for ((index, shop) in fruitShops.withIndex()) {
            val marker = MarkerOptions().position(coordinates[index]).title(shop.nameFruitShop)
            map.addMarker(marker)
        }

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates[0], 16f), 4000, null)
    }
}
