package com.oriol.ffapp.server


import com.oriol.ffapp.UserLogin
import com.oriol.ffapp.model.Fruit
import com.oriol.ffapp.model.FruitShop
import com.oriol.ffapp.model.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface APIService {


    @GET
    suspend fun getUsers(@Url url:String): Response<List<User>>
    @GET
    suspend fun getFruits(@Url url:String): Response<List<Fruit>>
    @GET
    suspend fun getFruitShops(@Url url:String): Response<List<FruitShop>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ruta/buscarUsersLogin")
    suspend fun postLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<User>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("ruta/buscarUsersLogin")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<User>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("Users/add")
     fun postRegister(@Body user: User): Call<Void>

}

