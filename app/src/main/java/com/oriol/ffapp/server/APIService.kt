package com.oriol.ffapp.server


import com.oriol.ffapp.model.Price
import com.oriol.ffapp.model.FruitShop
import com.oriol.ffapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url


interface APIService {


    @GET
    suspend fun getUsers(@Url url:String): Response<List<User>>
    @GET("Prices")
    suspend fun getPrices(@Url url:String): Response<List<Price>>
    @GET
    suspend fun getFruitShops(@Url url:String): Response<List<FruitShop>>
    @GET("Fruitshops")
    suspend fun getFruitShops1(): Response<List<FruitShop>>

    @GET("SearchUserLogin")
    suspend fun postLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<User>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Users/add")
    fun postRegister(@Body user: User): Call<Void>

}

