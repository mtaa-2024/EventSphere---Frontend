package stuba.fiit.sk.eventsphere.api

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {

    @GET("login")
    suspend fun getUser(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): JsonObject

    @GET("friends/search")
    suspend fun getFriendsSearch(
        @Query("filter") filter: String?
    ): JsonObject

    @GET("user")
    suspend fun getUserData(
        @Query("id") id: Int
    ): JsonObject

    @POST("register")
    suspend fun register(
        @Body registrationData: JsonObject
    ): JsonObject

    @POST("friend/add")
    suspend fun addFriend(
        @Body addFriendData: JsonObject
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
    suspend fun getUpcoming(
        @Query("education") education: Boolean,
        @Query("music") music: Boolean,
        @Query("food") food: Boolean,
        @Query("art") art: Boolean,
        @Query("sport") sport: Boolean
    ): JsonObject

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

    @GET("event/comments")
    suspend fun getUpdatedComments(
        @Query("id") id: Int
    ): JsonObject

    @PUT("profile/image")
    suspend fun updateImage (
        @Body body: JsonObject
    ): JsonObject

    @GET("username")
    suspend fun usernameExists (
        @Query("input") input: String,
        @Query("locale") locale: String,
    ): JsonObject

    @GET("email")
    suspend fun emailExists (
        @Query("input") input: String,
        @Query("locale") locale: String,
    ): JsonObject

    @GET("isFriend")
    suspend fun isFriend(
        @Query("user_id") id: Int,
        @Query("friend_id") friend_id: Int
    ): JsonObject

    @GET("event/search")
    suspend fun searchEvents(
        @Query("input") input: String
    ): JsonObject

    @POST("update/event")
    suspend fun updateEvent(
        @Body body: JsonObject
    ): JsonObject

}

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:9000/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)