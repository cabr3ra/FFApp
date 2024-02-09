package com.oriol.ffapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oriol.ffapp.model.User
import com.oriol.ffapp.model.UserProvider
import com.oriol.ffapp.rvUser.UserRvAdapter
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FruitShopList : AppCompatActivity() {
    var userList : MutableList<User> = UserProvider.users
    private lateinit var userRvAdapter: UserRvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit_shop_list)


        var llista_extres = intent.extras
        var nom = llista_extres?.get("NOM_PARAMETRE")
        println("NOM parametre: " + nom)


        var textView = findViewById<TextView>(R.id.username)
        textView.text = getString(R.string.bienvenido) + nom


        val rv_user = findViewById<RecyclerView>(R.id.rvUsers)
        rv_user.layoutManager = LinearLayoutManager(this)
        userRvAdapter = UserRvAdapter(userList)
        rv_user.adapter = userRvAdapter


        lifecycleScope.launch(Dispatchers.Default) {
            var con = Retrofit.Builder().baseUrl(Routes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            withContext(Dispatchers.IO) {
                try {
                    var resposta = con.create(APIService::class.java).getUser("userGET.php")

                    withContext(Dispatchers.Main) {
                        if (resposta.isSuccessful) {
                            val newUser = resposta.body() ?: emptyList()
                            userList.clear()
                            userList.addAll(newUser)
                            userRvAdapter.notifyDataSetChanged()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}