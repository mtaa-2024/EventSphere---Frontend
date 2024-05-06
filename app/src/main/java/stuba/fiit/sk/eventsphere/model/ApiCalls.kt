package stuba.fiit.sk.eventsphere.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.viewmodel.CategorySelectStates
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Locale
import java.util.UUID
import kotlin.math.log


val apiCalls = ApiCalls()

class ApiCalls {
    suspend fun createUser(register: RegisterData): User? {
        val body = JsonObject()
        body.addProperty("username", register.username.trim())
        body.addProperty("email", register.email.trim())
        body.addProperty("password", register.password.trim())
        body.addProperty("locale", Locale.getDefault().toString())
        try {
            val fetchedJson = apiService.registerUser(body)
            if (fetchedJson.get("result").asBoolean)
                return model.getUser(fetchedJson.get("user").asJsonObject)
            return null
        } catch (e: Exception) {
            println("Error: $e")
            return null
        }
    }

    suspend fun loginUser(login: LoginData): Pair<User?, String> {
        try {
            val fetchedJson =
                apiService.loginUser(username = login.username.trim(), password = login.password.trim())
            if (fetchedJson.get("result").asBoolean)
                return Pair(model.getUser(fetchedJson.get("user").asJsonObject), "")
            return Pair(null, fetchedJson.get("message").asString)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Pair(null, "Error")
    }

    suspend fun editUserProfile(actualUser: User, newUserData: NewUserData, userDataCopy: NewUserData, context: Context): User? {
        val body = JsonObject()
        body.addProperty("id", actualUser.id.toString())
        if (newUserData.firstname != null && newUserData.firstname != userDataCopy.firstname)
            body.addProperty("firstname", newUserData.firstname)
        if (newUserData.lastname != null && newUserData.lastname != userDataCopy.lastname)
            body.addProperty("lastname", newUserData.lastname)
        if (newUserData.email != null && newUserData.newEmail != null && newUserData.email != userDataCopy.email && newUserData.newEmail != userDataCopy.newEmail)
            body.addProperty("email", newUserData.newEmail)
        if (newUserData.profileImage != null) {
            val uri = newUserData.profileImage
            try {
                val inputStream = context.contentResolver.openInputStream(uri!!)
                inputStream?.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    val resizedBitmap = model.resizeBitmap(bitmap, 100)
                    val outputStream = ByteArrayOutputStream()
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Adjust compression quality as needed
                    val byteArray = outputStream.toByteArray()
                    val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    body.addProperty("profile_image", base64String)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val fetchedJson = apiService.editUser(body)
        println("Updating user")
        println(fetchedJson)
        return model.getUser(fetchedJson.get("user").asJsonObject)
    }
    suspend fun getFriendData(id: UUID): User? {
        val fetchedJson = apiService.getUser(id.toString())
        if (fetchedJson.get("result").asBoolean)
            return model.getUser(fetchedJson.get("user").asJsonObject)
        return null
    }

    suspend fun addFriend(loggedUser: User, friendUser: User) {
        val body = JsonObject()
        body.addProperty("user_id", loggedUser.id.toString())
        body.addProperty("friend_id", friendUser.id.toString())
        apiService.addFriend(body)
    }

    suspend fun isFriend(loggedUser: User, friendUser: User): Boolean {
        val fetchedJson = apiService.isFriend(loggedUser.id, friendUser.id)
        return fetchedJson.get("result").asBoolean
    }

    suspend fun getAllFriends(loggedUser: User): MutableList<User>? {
        val fetchedJson = apiService.getFriends(loggedUser.id)
        if (fetchedJson.get("result").asBoolean)
            return model.getUsers(fetchedJson.get("users").asJsonArray)
        return null
    }

    suspend fun searchUsers(filter: String): MutableList<User>? {
        val fetchedJson = apiService.getSearchFriend(filter)
        if (fetchedJson.get("result").asBoolean)
            return model.getUsers(fetchedJson.get("users").asJsonArray)
        return null
    }

    suspend fun getUpcomingEvents(categories: CategorySelectStates): MutableList<Event>? {
        val fetchedJson = apiService.getUpcomingEvents(categories.education, categories.music, categories.food, categories.art, categories.sport)
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }

    suspend fun searchEvents(categories: CategorySelectStates, filter: String): MutableList<Event>? {
        val fetchedJson = apiService.getSearchEvent(categories.education, categories.music, categories.food, categories.art, categories.sport, filter)
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }
    suspend fun searchEventsAttending(categories: CategorySelectStates, filter: String, loggedUser: User): MutableList<Event>? {
        val fetchedJson = apiService.getSearchAttendingEvent(categories.education, categories.music, categories.food, categories.art, categories.sport, filter, loggedUser.id.toString())
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }

    suspend fun getAttendingEvents(categories: CategorySelectStates, loggedUser: User): MutableList<Event>? {
        val fetchedJson = apiService.getAttendingEvents(categories.education, categories.music, categories.food, categories.art, categories.sport, loggedUser.id)
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }

    suspend fun getUpcomingUserEvents(loggedUser: User): MutableList<Event>? {
        val fetchedJson = apiService.getUpcomingEventsUser(loggedUser.id.toString())
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }

    suspend fun getExpiredUserEvents(loggedUser: User): MutableList<Event>? {
        val fetchedJson = apiService.getExpiredEventsUser(loggedUser.id.toString())
        if (fetchedJson.get("result").asBoolean)
            return model.getEvents(fetchedJson.get("events").asJsonArray)
        return null
    }

    suspend fun checkIfUsernameExists(input: String): Pair<Boolean, String> {
        val fetchedJson = apiService.checkUsername(input)
        if (fetchedJson.get("result").asBoolean)
            return Pair(false, fetchedJson.get("message").asString)
        return Pair(true, "")
    }

    suspend fun checkIfEmailExists(input: String): Pair<Boolean, String> {
        val fetchedJson = apiService.checkEmail(input)
        if (fetchedJson.get("result").asBoolean)
            return Pair(false, fetchedJson.get("message").asString)
        return Pair(true, "")
    }

    suspend fun createEvent(eventData: Event) {
        println(eventData)
        try {
            val body = JsonObject()
            body.addProperty("id", eventData.ownerId.toString())
            body.addProperty("title", eventData.title)
            body.addProperty("description", eventData.description)
            body.addProperty("location", eventData.location)
            body.addProperty("latitude", eventData.latitude)
            body.addProperty("longitude", eventData.longitude)
            body.addProperty("category", eventData.category)
            body.addProperty("estimated_end", eventData.estimatedEnd)
            val performersArray = JsonArray()
            eventData.performers?.forEach { performer ->
                val performerObject = JsonObject()
                performerObject.addProperty("id", performer.id.toString())
                performersArray.add(performerObject)
            }
            body.add("performers", performersArray)
            val fetchedJson = apiService.createEvent(body)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    suspend fun insertNewComment(comment: String, loggedUser: User, event: Event): Comment? {
        println(loggedUser.id)
        try {
            val body = JsonObject()
            body.addProperty("user_id", loggedUser.id.toString())
            body.addProperty("event_id", event.id.toString())
            body.addProperty("text", comment)
            body.addProperty("locale", Locale.getDefault().toString())
            val fetchedJson = apiService.insertComment(body)
            println(fetchedJson)
            if (fetchedJson.get("result").asBoolean)
                return model.getComment(fetchedJson.get("comment").asJsonObject)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getUserData(ownerId: UUID): User? {
        val fetchedJson = apiService.getUser(ownerId.toString())
        if (fetchedJson.get("result").asBoolean) {
            return model.getUser(fetchedJson.get("user").asJsonObject)
        }
        return null
    }

    suspend fun attendEvent(event: Event, loggedUser: User): Boolean {
        try {
            val body = JsonObject()
            body.addProperty("user_id", loggedUser.id.toString())
            body.addProperty("event_id", event.id.toString())
            val fetchedJson = apiService.attendEvent(body)
            return fetchedJson.get("result").asBoolean
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    suspend fun isAttending(event: Event, loggedUser: User): Boolean {
        val body = JsonObject()
        body.addProperty("user_id", loggedUser.id.toString())
        body.addProperty("event_id", event.id.toString())
        val fetchedJson = apiService.isAttendingEvent(body)
        println(fetchedJson)
        return fetchedJson.get("result").asBoolean
    }

    suspend fun updateEvent(body: JsonObject): Pair<Boolean, String> {
        println(body)
        val fetchedJson = apiService.updateEvent(body)
        return Pair(fetchedJson.get("result").asBoolean, fetchedJson.get("message").asString)
    }


}