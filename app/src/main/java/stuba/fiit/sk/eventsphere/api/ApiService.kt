package stuba.fiit.sk.eventsphere.api

import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.Locale
import java.util.UUID

interface ApiService {

    @POST("create/user")
    suspend fun registerUser (
        @Body body: JsonObject
    ): JsonObject

    @GET("login/user")
    suspend fun loginUser(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("locale") locale: String = Locale.getDefault().toString()
    ): JsonObject

    @POST("edit/profile")
    suspend fun editUser(
        @Body body: JsonObject
    ): JsonObject

    @GET("user")
    suspend fun getUser(
        @Query("id") id: String
    ): JsonObject

    @POST("add/friend")
    suspend fun addFriend(
        @Body body: JsonObject
    ): JsonObject

    @GET("is/friend")
    suspend fun isFriend(
        @Query("user_id") user_id: UUID,
        @Query("friend_id") friend_id: UUID
    ): JsonObject

    @GET("search/users")
    suspend fun getSearchFriend(
        @Query("filter") filter: String
    ): JsonObject

    @GET("user/friends")
    suspend fun getFriends(
        @Query("id") id: UUID
    ): JsonObject

    @GET("upcoming/events")
    suspend fun getUpcomingEvents(
        @Query("education") education: Boolean,
        @Query("music") music: Boolean,
        @Query("food") food: Boolean,
        @Query("art") art: Boolean,
        @Query("sport") sport: Boolean,
    ): JsonObject

    @GET("upcoming/events")
    suspend fun getAttendingEvents(
        @Query("education") education: Boolean,
        @Query("music") music: Boolean,
        @Query("food") food: Boolean,
        @Query("art") art: Boolean,
        @Query("sport") sport: Boolean,
        @Query("id") id: UUID
    ): JsonObject

    @GET("search/events")
    suspend fun getSearchEvent(
        @Query("education") education: Boolean,
        @Query("music") music: Boolean,
        @Query("food") food: Boolean,
        @Query("art") art: Boolean,
        @Query("sport") sport: Boolean,
        @Query("filter") filter: String
    ): JsonObject

    @GET("search/events")
    suspend fun getSearchAttendingEvent(
        @Query("education") education: Boolean,
        @Query("music") music: Boolean,
        @Query("food") food: Boolean,
        @Query("art") art: Boolean,
        @Query("sport") sport: Boolean,
        @Query("filter") filter: String,
        @Query("id") id: String
    ): JsonObject

    @GET("user/upcoming")
    suspend fun getUpcomingEventsUser(
        @Query("id") id: String
    ): JsonObject

    @GET("user/expired")
    suspend fun getExpiredEventsUser(
        @Query("id") id: String
    ): JsonObject

    @GET("exists/username")
    suspend fun checkUsername(
        @Query("username") input: String,
        @Query("locale") locale: String = Locale.getDefault().toString()
    ): JsonObject

    @GET("exists/email")
    suspend fun checkEmail(
        @Query("email") input: String,
        @Query("locale") locale: String = Locale.getDefault().toString()
    ): JsonObject

    @POST("create/event")
    suspend fun createEvent(
        @Body body: JsonObject
    ): JsonObject

    @POST("insert/comment")
    suspend fun insertComment(
        @Body body: JsonObject
    ): JsonObject

    @POST("attend/event")
    suspend fun attendEvent(
        @Body body: JsonObject
    ): JsonObject

    @POST("is/attending")
    suspend fun isAttendingEvent(
        @Body body: JsonObject
    ): JsonObject

    @POST("edit/event")
    suspend fun updateEvent(
        @Body body: JsonObject
    ): JsonObject
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://mtaa-421316.uc.r.appspot.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)