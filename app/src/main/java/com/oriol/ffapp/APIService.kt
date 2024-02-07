package com.oriol.ffapp

import com.oriol.ffapp.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getUser(@Url url:String): Response<List<User>>

    //@GET("{ruta}/get.php")
    //suspend fun getValues(@PATH("ruta") ruta:String, @Query("lletra") lletra:String): Response<List<User>>

    @POST("{ruta}/login.php")
    suspend fun postLogin(@Path("ruta")ruta:String, @Body userLogin:UserLogin):Response<User>
}
