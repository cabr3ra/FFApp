package com.oriol.ffapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oriol.ffapp.server.APIService
import com.oriol.ffapp.server.FruitDTO
import com.oriol.ffapp.server.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal

class AddFruitActivity : AppCompatActivity() {

    private lateinit var editTextFruitName: EditText
    private lateinit var editTextFruitPrice: EditText
    private lateinit var btnAddFruit: Button
    private var fruitShopId: Long = 0 // Obtener esto del intent o de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fruit)

        editTextFruitName = findViewById(R.id.editTextFruitName)
        editTextFruitPrice = findViewById(R.id.editTextFruitPrice)
        btnAddFruit = findViewById(R.id.btnAddFruit)

        // Supongamos que fruitShopId se pasa a esta actividad
        fruitShopId = intent.getLongExtra("FRUIT_SHOP_ID", 0)

        btnAddFruit.setOnClickListener {
            addFruit(it)
        }
    }

    private fun addFruit(view: View) {
        val name = editTextFruitName.text.toString()
        val priceText = editTextFruitPrice.text.toString()

        if (name.isNotEmpty() && priceText.isNotEmpty()) {
            val price = BigDecimal(priceText)

            CoroutineScope(Dispatchers.IO).launch {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Routes.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                val service = retrofit.create(APIService::class.java)
                val response = service.addFruit(FruitDTO(name, fruitShopId, price))

                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddFruitActivity, "Fruta añadida exitosamente", Toast.LENGTH_SHORT).show()
                        finish() // Close the activity
                    } else {
                        Toast.makeText(this@AddFruitActivity, "Error al añadir fruta", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}

