package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService

class EventViewModel(id: Int) : ViewModel() {
    private val _event = MutableLiveData<EventView>()
    val event: LiveData<EventView> = _event

    private val _performers = MutableLiveData<EventView>()
    val performers: LiveData<EventView> = _performers

    init {
        viewModelScope.launch{
            getEventData(id)
        }
    }

    suspend fun getEventData(id: Int) {
        try {
            val fetchedJson = apiService.getEvent(id)
            val performersList = mutableListOf<PerformersView>()
            val commentsList = mutableListOf<CommentsView>()

            if (!fetchedJson.get("performers").isJsonNull) {
                val performersArray = fetchedJson.getAsJsonArray("performers").asJsonArray
                performersArray.forEach { performerElement ->
                    val performerObject = performerElement.asJsonObject
                    val performerView = PerformersView (
                        id = if (performerObject.get("id").isJsonNull) null else performerObject.get("id").asInt,
                        firstname = if (performerObject.get("firstname").isJsonNull) null else performerObject.get("firstname").asString,
                        lastname = if (performerObject.get("lastname").isJsonNull) null else performerObject.get("lastname").asString,
                        profile_picture = if (performerObject.get("profile_image").isJsonNull) null else performerObject.get("profile_image").asString,
                    )
                    performersList.add(performerView)
                }
            }

            if (!fetchedJson.get("comments").isJsonNull) {
                val commentsArray = fetchedJson.getAsJsonArray("comments").asJsonArray
                commentsArray.forEach { commentElement ->
                    val commentObject = commentElement.asJsonObject
                    val commentView = CommentsView (
                        firstname = if (commentObject.get("firstname").isJsonNull) null else commentObject.get("firstname").asString,
                        lastname = if (commentObject.get("lastname").isJsonNull) null else commentObject.get("lastname").asString,
                        profile_picture = if (commentObject.get("profile_image").isJsonNull) null else commentObject.get("profile_image").asString,
                        text = if (commentObject.get("text").isJsonNull) null else commentObject.get("text").asString,
                    )
                    commentsList.add(commentView)
                }
            }

            val eventObject = fetchedJson.getAsJsonArray("event").get(0).asJsonObject
            val event = EventView(
                title = eventObject.get("title")?.asString,
                description = eventObject.get("description")?.asString,
                location = eventObject.get("location")?.asString,
                estimated_end = eventObject.get("estimated_end")?.asString,
                owner_firstname = eventObject.get("firstname")?.asString,
                owner_lastname = eventObject.get("lastname")?.asString,
                owner_picture = eventObject.get("profile_picture")?.asString,
                performers = performersList,
                comments = commentsList
            )
            _event.value = event
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

}
data class EventView (
    var title: String?,
    var description: String?,
    var location: String?,
    var estimated_end: String?,
    var owner_firstname: String?,
    var owner_lastname: String?,
    var owner_picture: String?,
    var performers: List<PerformersView>?,
    var comments: List<CommentsView>?
)

data class PerformersView (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?
)

data class CommentsView (
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?,
    var text: String?
)

class EventViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}