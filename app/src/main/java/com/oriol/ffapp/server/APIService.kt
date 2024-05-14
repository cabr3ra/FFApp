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

    @GET("FruitShops")
    suspend fun getFruitShops(): Response<List<FruitShop>>

    @GET("FruitShops/search")
    suspend fun searchFruitShopsByName(@Query("name") name: String): Response<List<FruitShop>>

    @GET("PricesWithName")
    suspend fun getPricesWithNames(): Response<List<List<Any>>>

    @GET
    suspend fun getUsers(@Url url:String): Response<List<User>>

    @GET("SearchUserLogin")
    suspend fun postLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<User>

    @POST("register")
    fun postRegister(@Body user: User): Call<Void>

}

