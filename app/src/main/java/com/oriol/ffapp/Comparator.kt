package com.oriol.ffapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.Fruit
import com.oriol.ffapp.model.FruitProvider
import com.oriol.ffapp.rvFruit.FruitRvAdapter
import com.oriol.ffapp.server.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Comparator : AppCompatActivity() {
    var fruitList : MutableList<Fruit> = FruitProvider.fruits
    private lateinit var fruitRvAdapter: FruitRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comparator)

        var llista_extres = intent.extras
        var nom = llista_extres?.get("NOM_PARAMETRE")
        println("NOM parametre: " + nom)

        val rv_fruit = findViewById<RecyclerView>(R.id.rvFruits)
        rv_fruit.layoutManager = LinearLayoutManager(this)
        fruitRvAdapter = FruitRvAdapter(fruitList)
        rv_fruit.adapter = fruitRvAdapter

        lifecycleScope.launch(Dispatchers.Default) {
            var connexio = Retrofit.Builder().baseUrl("http://192.168.22.103:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            withContext(Dispatchers.IO) {
                try {
                    var resposta = connexio.create(APIService::class.java).getFruit("CARPETA_PHP/fruitGET.php")

                    withContext(Dispatchers.Main) {
                        if (resposta.isSuccessful) {
                            val newFruit = resposta.body() ?: emptyList()
                            fruitList.clear()
                            fruitList.addAll(newFruit)
                            fruitRvAdapter.notifyDataSetChanged()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }


        /*
               var searchView = findViewById<SearchView>(R.id.svFruits)
               searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener) {
                   override fun onQueryTextChange(newText: String?) Boolean {
                   return true;
                   }
                   override fun onQueryTextSubmit(query: String?) Boolean {
                       if(!query?.isNullOnEmpty()!!){
                           getUserByName(query)
                       }
                       return true;
                   }
               }


           }


           private fun getUserByName(query:String) {
               lifecycleScope.launch {
                   var connexio = Retrofit.Builder().baseUrl("http://192.168.22.103:8888/")
                       .addConverterFactory(GsonConverterFactory.create())
                       .build()
                       var resposta = connexio.create(APIService::class.java).getFruit("clients", query)


                       withContext(Dispatchers.Main) {
                           if (resposta.isSuccessful) {
                               val newFruit = resposta.body() ?: emptyList()
                               fruitList.clear()
                               fruitList.addAll(newUser)
                               fruitRvAdapter.notifyDataSetChanged()
                           }
                       }

               }
               */
    }
}