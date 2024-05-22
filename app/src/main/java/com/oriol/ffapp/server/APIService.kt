package com.oriol.ffapp.server


import com.oriol.ffapp.model.FruitShop
import com.oriol.ffapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @GET("FruitShops")
    suspend fun getFruitShops(): Response<List<FruitShop>>

    @GET("FruitShops/search")
    suspend fun searchFruitShopsByName(@Query("name") name: String): Response<List<FruitShop>>

    @GET("PricesWithName")
    suspend fun getPricesWithNames(): Response<List<List<Any>>>

    @GET("PricesSortedAsc")
    suspend fun getPriceAsc(): Response<List<List<Any>>>

    @GET("PricesSortedDesc")
    suspend fun getPriceDesc(): Response<List<List<Any>>>

    @GET("Prices/search")
    suspend fun searchComparatorByName(@Query("name") name: String): Response<List<List<Any>>>

    @GET("Users")
    suspend fun getUsers(): Response<List<User>>

    @GET("SearchUserLogin")
    suspend fun postLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<User>

    @POST("Users/add")
    fun postRegister(@Body user: User): Call<Void>

    @PUT("Users/update")
    suspend fun putUpdateUser(@Body user: User): Response<Void>

    @DELETE("Users/delete/{userId}")
    suspend fun deleteUser(@Path("userId") userId: Int): Response<Void>
}

