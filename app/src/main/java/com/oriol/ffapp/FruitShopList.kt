package com.oriol.ffapp


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.FruitShop
import com.oriol.ffapp.model.FruitShopProvider
import com.oriol.ffapp.rvFruitShop.FruitShopRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FruitShopList : AppCompatActivity() {
    //var userList : MutableList<User> = UserProvider.users
    var fruitShopList : MutableList<FruitShop> = FruitShopProvider.fruitShop
    private lateinit var fruitShopRvAdapter: FruitShopRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_shop_list)

        var llista_extres = intent.extras
        var nom = llista_extres?.get("NOM_PARAMETRE")
        println("Nom parametre: " + nom)

        var textView = findViewById<TextView>(R.id.tvFruitShop)
        textView.text = getString(R.string.bienvenido) + nom

        val rv_fruitShop = findViewById<RecyclerView>(R.id.rvFruitShop)
        rv_fruitShop.layoutManager = LinearLayoutManager(this)
        fruitShopRvAdapter = FruitShopRvAdapter(fruitShopList)
        rv_fruitShop.adapter = fruitShopRvAdapter

        lifecycleScope.launch(Dispatchers.Default) {
            var con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            withContext(Dispatchers.IO) {
                try {
                    var resposta = con.create(APIService::class.java).getFruitShops("FruitShops")

                    withContext(Dispatchers.Main) {
                        if (resposta.isSuccessful) {
                            val newFruitShop = resposta.body()?: emptyList()
                            fruitShopList.clear()
                            fruitShopList.addAll(newFruitShop)
                            fruitShopRvAdapter.notifyDataSetChanged()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}