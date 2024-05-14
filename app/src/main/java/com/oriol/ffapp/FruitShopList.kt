package com.oriol.ffapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvFruitShop.FruitShopRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.appcompat.widget.SearchView

class FruitShopList : AppCompatActivity() {
    private lateinit var fruitShopRvAdapter: FruitShopRvAdapter
    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_shop_list)

        apiService = RetrofitClient.apiService

        setupRecyclerView()
        setupSearchView()
        loadFruitShops()
    }

    private fun setupRecyclerView() {
        val rvFruitShop = findViewById<RecyclerView>(R.id.rvFruitShop)
        rvFruitShop.layoutManager = LinearLayoutManager(this)
        fruitShopRvAdapter = FruitShopRvAdapter(emptyList())
        rvFruitShop.adapter = fruitShopRvAdapter
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.svFruitShop)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchFruitShops(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchFruitShops(it) }
                return true
            }
        })
    }

    private fun loadFruitShops() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = apiService.getFruitShops()
            if (response.isSuccessful) {
                val fruitShops = response.body()
                fruitShops?.let {
                    fruitShopRvAdapter.updateFruitShops(it)
                }
            } else {
                println("ERROR")
            }
        }
    }

    private fun searchFruitShops(query: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = apiService.searchFruitShopsByName(query)
            if (response.isSuccessful) {
                val fruitShops = response.body()
                fruitShops?.let {
                    fruitShopRvAdapter.updateFruitShops(it)
                }
            } else {
                println("ERROR")
            }
        }
    }
}
