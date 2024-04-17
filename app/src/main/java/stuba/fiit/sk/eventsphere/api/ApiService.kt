package stuba.fiit.sk.eventsphere.api

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("login")
    suspend fun getUser(
        @Query("username") username: String,
        @Query("password") password: String
    ): JsonObject

    @GET("user")
    suspend fun getUserData(
        @Query("id") id: Int
    ): JsonObject

    @POST("register")
    suspend fun register(
        @Body registrationData: JsonObject
    ): JsonObject

    @GET("friends")
    suspend fun getFriends(
        @Query("id") id: Int?
    ): JsonObject

    @POST("user/edit")
    suspend fun editUser(
        @Body editUserData: JsonObject
    ): JsonObject

    @GET("upcoming")
    suspend fun getUpcoming(): JsonObject

    @GET("attending")
    suspend fun getAttending(
        @Query("id") id: Int?
    ): JsonObject

    @GET("event")
    suspend fun getEvent(
        @Query("id") id: Int
    ): JsonObject

    @GET("upcoming/owner")
    suspend fun getUpcomingOwner(
        @Query("id") id: Int?
    ): JsonObject

    @GET("expired/owner")
    suspend fun getExpiredOwner(
        @Query("id") id: Int?
    ): JsonObject

    @POST("create/event")
    suspend fun createEvent(
        @Body eventData: JsonObject
    ): JsonObject

    @POST("comment")
    suspend fun insertComment(
        @Body body: JsonObject
    ): JsonObject
}

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)