package stuba.fiit.sk.eventsphere.api

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("event")
    suspend fun getEvent(): JsonObject
}

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:4000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)