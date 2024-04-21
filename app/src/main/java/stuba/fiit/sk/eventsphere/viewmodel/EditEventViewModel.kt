package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.CommentStruct
import stuba.fiit.sk.eventsphere.model.EventOutput
import stuba.fiit.sk.eventsphere.model.FriendPerformer
import stuba.fiit.sk.eventsphere.model.LocationData

class EditEventViewModel (id: Int) : ViewModel() {
    private val eventId = id

    private val _event = MutableLiveData<EventOutput>()
    val event: LiveData<EventOutput> = _event

    private val performersList: MutableList<FriendPerformer> = mutableListOf()
    val friendsList: MutableList<FriendPerformer> = mutableListOf()

    init {
        viewModelScope.launch {
            getEvent()
            getFriends()
        }
    }

    suspend fun getFriends() {
        try {
            val fetchedJson = apiService.getFriends(_event.value?.owner_id)
            if (fetchedJson.get("result").asBoolean) {
                friendsList.clear()
                val friendsArray = fetchedJson.getAsJsonArray("friends").asJsonArray
                friendsArray.forEach { friendsElement ->
                    val friendsObject = friendsElement.asJsonObject
                    val friendsView = FriendPerformer(
                        id = if (friendsObject.get("id").isJsonNull) null else friendsObject.get("id").asInt,
                        firstname = if (friendsObject.get("firstname").isJsonNull) null else friendsObject.get(
                            "firstname"
                        ).asString,
                        lastname = if (friendsObject.get("lastname").isJsonNull) null else friendsObject.get(
                            "lastname"
                        ).asString,
                        profile_picture = null,
                    )
                    if (_event.value?.performers?.contains(friendsView) == false)
                        friendsList.add(friendsView)
                }
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    private suspend fun getEvent() {
        try {
            val fetchedJson = apiService.getEvent(eventId)
            val commentsList = mutableListOf<CommentStruct>()


            if (!fetchedJson.get("performers").isJsonNull) {
                performersList.clear()
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
                address = if (eventObject.get("location").isJsonNull) "" else eventObject.get("location").asString ?: "",
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
            println(_event.value)
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun updateDescription(s: String) {
        _event.value?.description = s
    }

    fun updateTitle(s: String) {
        _event.value?.title = s
    }

    fun addPerformer(friend: FriendPerformer) {
        _event.value?.performers?.add(friend)
    }

    fun removePerformer(performer: FriendPerformer?) {
        if (performer != null) {
            _event.value?.performers?.remove(performer)
            if ((performer.id ?: -1) != -1)
                friendsList.add(performer)
        }
    }

    fun updateLocation(input: LocationData) {
        _event.value?.location = input
    }

    suspend fun updateEvent(): Pair<Boolean, String> {
        try {
            val body = JsonObject()
            body.addProperty("id", _event.value?.event_id)
            body.addProperty("title", _event.value?.title)
            body.addProperty("description", _event.value?.description)
            body.addProperty("location", _event.value?.location?.address)
            body.addProperty("latitude", _event.value?.location?.latitude)
            body.addProperty("longitude", _event.value?.location?.longitude)

            val performersArray = JsonArray()
            _event.value?.performers?.forEach { performer ->
                val performerObject = JsonObject()
                performerObject.addProperty("id", performer.id)
                performersArray.add(performerObject)
            }
            body.add("performers", performersArray)

            val fetchedJson = apiService.updateEvent(body)
            return Pair(fetchedJson.get("result").asBoolean, "")
        } catch (e : Exception) {
            println(e)
        }
        return Pair(false, "Error updating event")
    }

}

class EditEventViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditEventViewModel::class.java)) {
            return EditEventViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
