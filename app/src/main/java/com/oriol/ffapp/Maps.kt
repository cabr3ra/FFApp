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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = RetrofitClient.apiService
        setContentView(R.layout.activity_maps)
        createFragment()
        adapter = FruitShopRvAdapter(emptyList()) // Inicializamos el adaptador con una lista vacía
    }

    private fun createFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun obtenerDatosTiendasFrutas() {
        println("AAAAAAAAAAAAAAA")
        GlobalScope.launch(Dispatchers.Main) {
            val response = apiService.getFruitShops()
            println("Respuesta recibida: $response")
            if (response.isSuccessful) {
                val fruitShops = response.body()
                fruitShops?.let {
                    println("Datos de las tiendas de frutas recibidos:")
                    println(it) // Imprime los datos recibidos
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
    // Llamamos al método para obtener los datos de las tiendas de frutas
    }

    private fun createMarkers() {
        for (i in 0 until adapter.itemCount) {
            val fruitShop = adapter.getFruitShop(i)
            fruitShop.horizontal?.let { horizontal ->
                fruitShop.vertical?.let { vertical ->
                    val coordinates = LatLng(horizontal, vertical)
                    val marker = MarkerOptions().position(coordinates).title(fruitShop.nameFruitShop)
                    map.addMarker(marker)
                }
            }
        }
        if (adapter.itemCount > 0) {
            val firstFruitShop = adapter.getFruitShop(0)
            firstFruitShop.horizontal?.let { horizontal ->
                firstFruitShop.vertical?.let { vertical ->
                    val coordinates = LatLng(horizontal, vertical)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 16f), 4000, null)
                }
            }
        }
    }
}
