package stuba.fiit.sk.eventsphere.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.util.UUID

val model = Models()

class Models {

    private fun jsonArrayToByteArray(jsonArray: JsonArray): ByteArray {
        val byteArray = ByteArray(jsonArray.size())
        for (i in 0 until jsonArray.size())
            byteArray[i] = jsonArray[i].asInt.toByte()
        return byteArray
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val scale = maxWidth.toFloat() / originalWidth.toFloat()
        val newWidth = (originalWidth * scale).toInt()
        val newHeight = (originalHeight * scale).toInt()
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun getProfileImage(userObject: JsonObject): ImageBitmap? {
        var bitmap: ImageBitmap? = null
        val imageArray = if (userObject.get("profile_image").isJsonNull) null else userObject.getAsJsonObject("profile_image").getAsJsonArray("data")
        if (imageArray != null) {
            val image = jsonArrayToByteArray(imageArray).decodeToString()
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            val imageBitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
            bitmap= imageBitMap.asImageBitmap()
        }
        return bitmap
    }


    fun getUser(userObject: JsonObject): User {
        val id = userObject.get("id").asString
        return User (
            id = UUID.fromString(id),
            username = userObject.get("username").asString,
            email = userObject.get("email").asString,
            firstname = if (userObject.get("firstname").isJsonNull) null else userObject.get("firstname").asString,
            lastname = if (userObject.get("lastname").isJsonNull) null else userObject.get("lastname").asString,
            profileImage = getProfileImage(userObject),
        )
    }

    fun getUsers(userArray: JsonArray): MutableList<User> {
        val list = mutableListOf<User>()
        if (userArray.isEmpty)
            return list
        userArray.forEach { element ->
            getUser(element.asJsonObject).let { list.add(it) }
        }
        return list
    }

    fun getComment(commentObject: JsonObject): Comment {
        val id = commentObject.get("id").asString
        return Comment (
            id = UUID.fromString(id),
            firstname = if (commentObject.get("firstname").isJsonNull) null else commentObject.get("firstname").asString,
            lastname = if (commentObject.get("lastname").isJsonNull) null else commentObject.get("lastname").asString,
            profileImage = getProfileImage(commentObject),
            text = commentObject.get("text").asString
        )
    }

    private fun getComments(commentsArray: JsonArray): MutableList<Comment> {
        val list = mutableListOf<Comment>()
        commentsArray.forEach { element ->
            list.add(getComment(element.asJsonObject))
        }
        return list
    }

    private fun getEvent(eventObject: JsonObject): Event {
        val event = eventObject.get("event").asJsonObject
        val id = event.get("id").asString
        val owner = event.get("owner_id").asString
        return Event (
            id = UUID.fromString(id),
            ownerId = UUID.fromString(owner),
            title = event.get("title").asString,
            description = event.get("description").asString,
            location = event.get("location").asString,
            latitude = event.get("latitude").asDouble,
            longitude = event.get("longitude").asDouble,
            category = event.get("category_id").asInt,
            estimatedEnd = event.get("formatted_estimated_end").asString,
            performers = getUsers(eventObject.get("performers").asJsonArray),
            comments = getComments(eventObject.get("comments").asJsonArray)
        )
    }

    fun getEvents(eventArray: JsonArray): MutableList<Event> {
        val list = mutableListOf<Event>()
        eventArray.forEach { element ->
            getEvent(element.asJsonObject).let { list.add(it) }
        }
        return list
    }
}

