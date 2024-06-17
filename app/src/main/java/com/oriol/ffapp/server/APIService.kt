package com.oriol.ffapp.server

import com.oriol.ffapp.model.FruitShop
import com.oriol.ffapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.math.BigDecimal

data class FruitDTO(val name: String, val fruitShopId: Long, val price: BigDecimal)
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

    @POST("/fruits/add")
    suspend fun addFruit(@Body fruitDTO: FruitDTO): Response<FruitDTO>
}


