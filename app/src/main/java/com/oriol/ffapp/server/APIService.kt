package com.oriol.ffapp.server

import com.oriol.ffapp.UserLogin
import com.oriol.ffapp.model.Fruit
import com.oriol.ffapp.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {

    @GET
    suspend fun getUser(@Url url:String): Response<List<User>>

    @GET
    suspend fun getFruit(@Url url:String): Response<List<Fruit>>

    //@GET("{ruta}/userGET.php")
    //suspend fun getValues(@PATH("ruta") ruta:String, @Query("lletra") lletra:String): Response<List<User>>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("{ruta}/login/login.php")
    suspend fun postLogin(@Path("ruta") ruta: String, @Body userLogin: UserLogin): Response<User>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("ruta/autenticar") // Endpoint para autenticación
    suspend fun autenticar(@Body credenciales: UserLogin): Response<TokenRespuesta>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("ruta/buscarUsuarioLogin") // Endpoint seguro que requiere autenticación
    suspend fun consultaSegura(@Header("Authorization") token: String): Response<ResultadoConsulta>

}
