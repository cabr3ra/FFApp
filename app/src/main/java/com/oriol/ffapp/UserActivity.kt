package com.oriol.ffapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.User
import com.oriol.ffapp.model.UserProvider
import com.oriol.ffapp.rvUser.UserRvAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {
    var userList : MutableList<User> = UserProvider.users
    private lateinit var userRvAdapter:UserRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        var llista_extres = intent.extras
        var nom = llista_extres?.get("NOM_PARAMETRE")
        println("NOM parametre:" + nom)

        var textView = findViewById<TextView>(R.id.username)
        textView.text = getString(R.string.bienvenido) + nom

        val rv_user = findViewById<RecyclerView>(R.id.rvUsers)
        rv_user.layoutManager = LinearLayoutManager(this)
        userRvAdapter = UserRvAdapter(userList)
        rv_user.adapter = userRvAdapter

        lifecycleScope.launch(Dispatchers.Default) {
            var connexio = Retrofit.Builder().baseUrl("http://192.168.22.103:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            withContext(Dispatchers.IO) {
                var resposta = connexio.create(APIService::class.java).getUser("CARPETA_PHP/userGET.php")

                withContext(Dispatchers.Main) {
                    if (resposta.isSuccessful) {
                        val newUser = resposta.body() ?: emptyList()
                        userList.clear()
                        userList.addAll(newUser)
                        userRvAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

/*
        var searchView = findViewById<SearchView>(R.id.svUsers)
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
            var connexio = Retrofit.Builder().baseUrl("http://172.18.4.97:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                var resposta = connexio.create(APIService::class.java).getUser("clients", query)

                withContext(Dispatchers.Main) {
                    if (resposta.isSuccessful) {
                        val newUser = resposta.body() ?: emptyList()
                        userList.clear()
                        userList.addAll(newUser)
                        userRvAdapter.notifyDataSetChanged()
                    }
                }

        }*/
    }
}