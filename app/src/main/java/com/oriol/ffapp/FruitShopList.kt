package com.oriol.ffapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.rvFruitShop.FruitShopRvAdapter
import com.oriol.ffapp.model.FruitShopProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FruitShopList : AppCompatActivity() {
    private lateinit var fruitShopRvAdapter: FruitShopRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_shop_list)

        setupRecyclerView()
        displayFruitShops()
    }

    private fun setupRecyclerView() {
        val rvFruitShop = findViewById<RecyclerView>(R.id.rvFruitShop)
        rvFruitShop.layoutManager = LinearLayoutManager(this)
        fruitShopRvAdapter = FruitShopRvAdapter(FruitShopProvider.fruitShop)
        rvFruitShop.adapter = fruitShopRvAdapter
    }

    private fun displayFruitShops() {
        // No es necesario llamar a la API, ya que los datos están disponibles en FruitShopProvider
        fruitShopRvAdapter.notifyDataSetChanged() // Notifica al adaptador sobre los cambios en los datos
    }
}




/*
class FruitShopList : AppCompatActivity() {
    var fruitShopList : MutableList<FruitShop> = FruitShopProvider.fruitShop
    private lateinit var fruitShopRvAdapter: FruitShopRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_shop_list)

        val rv_fruitShop = findViewById<RecyclerView>(R.id.rvFruitShop)
        rv_fruitShop.layoutManager = LinearLayoutManager(this)
        fruitShopRvAdapter = FruitShopRvAdapter(fruitShopList)
        rv_fruitShop.adapter = fruitShopRvAdapter

        lifecycleScope.launch(Dispatchers.Default) {
                val con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val resposta = con.create(APIService::class.java).getFruitShops("FruitShops")

                withContext(Dispatchers.Main) {
                    if (resposta.isSuccessful) {
                        val newFruitShop = resposta.body() ?: emptyList()
                        fruitShopList.clear()
                        fruitShopList.addAll(newFruitShop)
                        fruitShopRvAdapter.notifyDataSetChanged()
                    } else {
                        // Manejar error de respuesta no exitosa
                        Log.e("FruitShopList", "Error obteniendo datos: ${resposta.message()}")
                    }
                }
        }
    }
}*/