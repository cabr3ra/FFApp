package com.oriol.ffapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.oriol.ffapp.model.Price
import com.oriol.ffapp.rvPrice.PriceRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.Dispatchers
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
        setupSearchView()
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

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.svFruits)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchFruits(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searchFruits(newText)
                }
                return true
            }
        })
    }

    private fun searchFruits(query: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = apiService.searchComparatorByName(query)
            if (response.isSuccessful) {
                val rawList = response.body() ?: emptyList()
                val prices = rawList.map { Price.fromArray(it) }
                withContext(Dispatchers.Main) {
                    priceRvAdapter.setData(prices)
                }
            } else {
                println("ERROR")
            }
        }
    }
}


