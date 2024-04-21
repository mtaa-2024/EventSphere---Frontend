package stuba.fiit.sk.eventsphere.viewmodel

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.CommentStruct
import stuba.fiit.sk.eventsphere.model.EventOutput
import stuba.fiit.sk.eventsphere.model.FriendPerformer
import stuba.fiit.sk.eventsphere.model.LocationData
import stuba.fiit.sk.eventsphere.model.User

class EventViewModel(id: Int) : ViewModel() {
    private val eventId = id

    private val _event = MutableLiveData<EventOutput>()
    val event: LiveData<EventOutput> = _event


    init {
        viewModelScope.launch{
            getEventData()
        }
    }

    private suspend fun getEventData() {
        try {
            val fetchedJson = apiService.getEvent(eventId)

            val performersList = mutableListOf<FriendPerformer>()
            val commentsList = mutableListOf<CommentStruct>()

            if (!fetchedJson.get("performers").isJsonNull) {
                val performersArray = fetchedJson.getAsJsonArray("performers").asJsonArray
                if (!performersArray.isEmpty) {
                    performersArray.forEach { performerElement ->
                        val performerObject = performerElement.asJsonObject
                        val performerView = FriendPerformer(
                            id =  performerObject.get("user_id").asInt,
                            firstname = if (performerObject.get("firstname").isJsonNull) null else performerObject.get("firstname")?.asString ?: "",
                            lastname = if (performerObject.get("lastname").isJsonNull) null else performerObject.get("lastname")?.asString ?: "",
                            profile_picture = null,
                        )
                        performersList.add(performerView)
                    }
                }
            }
            if (!fetchedJson.get("comments").isJsonNull) {
                val commentsArray = fetchedJson.getAsJsonArray("comments").asJsonArray
                if (!commentsArray.isEmpty) {
                    commentsArray.forEach { commentElement ->
                        val commentObject = commentElement.asJsonObject
                        val commentView = CommentStruct(
                            id = commentObject.get("id").asInt,
                            firstname = if (commentObject.get("firstname").isJsonNull) null else commentObject.get("firstname").asString ?: "",
                            lastname = if (commentObject.get("lastname").isJsonNull) null else commentObject.get("lastname").asString ?: "",
                            profile_image = null,
                            text = commentObject.get("text").asString ?: "",
                        )
                        commentsList.add(commentView)
                    }
                }
            }

            val eventObject = fetchedJson.getAsJsonArray("event").get(0).asJsonObject

            val location = LocationData (
                address = eventObject.get("location").asString ?: "",
                latitude = eventObject.get("latitude").asDouble,
                longitude = eventObject.get("longitude").asDouble
            )



            val event = EventOutput (
                event_id = eventObject.get("id").asInt,
                title = eventObject.get("title").asString ?: "",
                description = eventObject.get("description").asString ?: "",
                location = location,
                category = eventObject.get("category_id").asInt,
                estimated_end = eventObject.get("estimated_end").asString ?: "",
                owner_id = eventObject.get("owner_id").asInt,
                owner_firstname = eventObject.get("firstname").asString ?: "",
                owner_lastname = eventObject.get("lastname").asString ?: "",
                owner_profile_image = null,
                performers = performersList,
                comments = commentsList
            )
            _event.value = event
            println(event)
        } catch (e: Exception) {
            println("Error: $e")
        }
    }


    suspend fun insertCommentNew (comment: String, loggedUser: LiveData<User>) {
        try {
            val jsonBody = JsonObject()
            jsonBody.addProperty("id", loggedUser.value?.id)
            jsonBody.addProperty("event_id", eventId)
            jsonBody.addProperty("commentValue", comment)

            val commentState = apiService.insertComment(jsonBody)

            if (commentState.get("result").asBoolean) {
                val fetchedJson = apiService.getUpdatedComments(eventId)
                val commentsList = mutableListOf<CommentStruct>()
                if (!fetchedJson.get("comments").isJsonNull) {
                    val commentsArray = fetchedJson.getAsJsonArray("comments").asJsonArray
                    if (!commentsArray.isEmpty) {
                        commentsArray.forEach { commentElement ->
                            val commentObject = commentElement.asJsonObject

                            var bitmap: ImageBitmap? = null

                            val imageArray = if (commentObject.get("profile_image").isJsonNull) null else commentObject.getAsJsonObject("profile_image").getAsJsonArray("data")
                            if (imageArray != null) {
                                val image = jsonArrayToByteArray(imageArray).decodeToString()
                                val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
                                val imageBitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
                                bitmap= imageBitMap.asImageBitmap()
                            }

                            val commentView = CommentStruct (
                                id = commentObject.get("id").asInt,
                                firstname = if (commentObject.get("firstname").isJsonNull) null else commentObject.get("firstname").asString ?: "",
                                lastname = if (commentObject.get("lastname").isJsonNull) null else commentObject.get("lastname").asString ?: "",
                                profile_image = bitmap,
                                text = commentObject.get("text").asString ?: "",
                            )
                            commentsList.add(commentView)
                        }
                    }
                }
                _event.value = _event.value?.copy(comments = commentsList)
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }

}

class EventViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}