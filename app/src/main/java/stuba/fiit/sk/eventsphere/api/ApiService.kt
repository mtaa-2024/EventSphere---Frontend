package stuba.fiit.sk.eventsphere.api

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("event")
    suspend fun getEvent(): JsonObject

    @GET("login")
    suspend fun getUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): JsonObject
}

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:4000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)