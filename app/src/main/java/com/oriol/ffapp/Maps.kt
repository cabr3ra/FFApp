// Activity Maps
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
import com.oriol.ffapp.rvFruitShop.FruitShopRvAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.RetrofitClient

class Maps : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var apiService: APIService
    private lateinit var map: GoogleMap
    private lateinit var adapter: FruitShopRvAdapter
    private var shopNameToHighlight: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitClient.apiService
        setContentView(R.layout.activity_maps)

        // Obtener el nombre de la tienda de los extras
        shopNameToHighlight = intent.getStringExtra("SHOP_NAME")

        createFragment()
        adapter = FruitShopRvAdapter(emptyList())
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun obtenerDatosTiendasFrutas() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = apiService.getFruitShops()
            if (response.isSuccessful) {
                val fruitShops = response.body()
                fruitShops?.let {
                    updateAdapterWithNewData(it)
                }
            } else {
                println("ERROR")
            }
        }
    }

    private fun updateAdapterWithNewData(fruitShops: List<FruitShop>) {
        adapter.updateFruitShops(fruitShops)
        createMarkers()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        obtenerDatosTiendasFrutas()
    }

    private fun createMarkers() {
        var markerToHighlight: MarkerOptions? = null

        for (i in 0 until adapter.itemCount) {
            val fruitShop = adapter.getFruitShop(i)
            fruitShop.horizontal?.let { horizontal ->
                fruitShop.vertical?.let { vertical ->
                    val coordinates = LatLng(horizontal, vertical)
                    val marker = MarkerOptions().position(coordinates).title(fruitShop.nameFruitShop)

                    if (fruitShop.nameFruitShop == shopNameToHighlight) {
                        // Establece el snippet para la tienda resaltada
                        marker.snippet(fruitShop.nameFruitShop)

                        // Agrega el marcador al mapa y guarda una referencia si es necesario
                        markerToHighlight = marker
                        map.addMarker(marker)?.showInfoWindow() // Muestra el snippet
                    } else {
                        // Agrega el marcador al mapa sin mostrar el snippet
                        map.addMarker(marker)
                        markerToHighlight = marker
                    }
                }
            }
        }


        markerToHighlight?.let { marker ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 16f), 4000, null)
        }
    }
}
