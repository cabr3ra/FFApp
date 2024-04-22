package com.oriol.ffapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvPrice.PriceRvAdapter
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Comparator : AppCompatActivity() {
    private lateinit var priceRvAdapter: PriceRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparator)

        // Obtiene los extras de la intención
        val extras = intent.extras
        val nom = extras?.getString("NOM_PARAMETRE")
        println("NOM parametre: $nom")

        // Configura el RecyclerView
        val rvPrices = findViewById<RecyclerView>(R.id.rvPrices)
        rvPrices.layoutManager = LinearLayoutManager(this)
        priceRvAdapter = PriceRvAdapter(emptyList())
        rvPrices.adapter = priceRvAdapter

        // Obtiene los datos de la API y los establece en el adaptador
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getPrices("Prices")
                if (response.isSuccessful) {
                    val prices = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        priceRvAdapter.setData(prices)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
