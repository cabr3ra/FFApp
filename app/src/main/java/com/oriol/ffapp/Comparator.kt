package com.oriol.ffapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.R
import com.oriol.ffapp.model.Price
import com.oriol.ffapp.rvPrice.PriceRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Comparator : AppCompatActivity() {
    private lateinit var priceRvAdapter: PriceRvAdapter
    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparator)

        apiService = RetrofitClient.apiService

        setupRecyclerView()
        loadPrices()
    }

    private fun setupRecyclerView() {
        val rvPrices = findViewById<RecyclerView>(R.id.rvPrices)
        rvPrices.layoutManager = LinearLayoutManager(this)
        priceRvAdapter = PriceRvAdapter(emptyList())
        rvPrices.adapter = priceRvAdapter
    }

    private fun loadPrices() {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = apiService.getPricesWithNames()
            if (response.isSuccessful) {
                val rawList = response.body() ?: emptyList()
                val prices = rawList.map { Price.fromArray(it) }
                withContext(Dispatchers.Main) {
                    priceRvAdapter.setData(prices)
                }
            } else {
                println("Error")
            }
        }
    }
}

