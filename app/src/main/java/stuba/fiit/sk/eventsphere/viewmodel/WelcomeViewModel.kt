package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.ApiService
import stuba.fiit.sk.eventsphere.api.Event
import stuba.fiit.sk.eventsphere.api.apiService

val gson = Gson()
class WelcomeViewModel(private val apiService: ApiService) : ViewModel() {
    private var _event = MutableLiveData<Event>()

    /*
    init {
        viewModelScope.launch {
            try {
                val fetchedJson = apiService.getEvent()
                val jsonObject = gson.fromJson(fetchedJson, JsonObject::class.java)

                val eventObject = jsonObject.getAsJsonArray("event")[0].asJsonObject
                val commentsObject = jsonObject.getAsJsonArray("comments").asJsonArray
                val performersObject = jsonObject.getAsJsonArray("performers").asJsonArray

                val eventList = EventStruct (
                    title = parsejson("title", eventObject),
                    description = parsejson("description", eventObject),
                    location = parsejson("location", eventObject),
                    estimated_end = parsejson("estimated_end", eventObject),
                    firstname = parsejson("firstname", eventObject),
                    lastname = parsejson("lastname", eventObject),
                    profile_image = parsejson("profile_image", eventObject),
                )

                val commentsList = mutableListOf<CommentStruct>()
                commentsObject.forEach { commentElement ->
                    val commentObject = commentElement.asJsonObject
                    val comment = CommentStruct(
                        firstname = parsejson("firstname", commentObject),
                        lastname = parsejson("lastname", commentObject),
                        profile_image = parsejson("profile_image", commentObject),
                        text = parsejson("text", commentObject)
                    )
                    commentsList.add(comment)
                }

                val performersList = mutableListOf<PerformerStruct>()
                performersObject.forEach { performerElement ->
                    val performerObject = performerElement.asJsonObject
                    val performer = PerformerStruct(
                        id = parsejson("id", performerObject)?.toInt(),
                        firstname = parsejson("firstname", performerObject),
                        lastname = parsejson("lastname", performerObject),
                        profile_image = parsejson("profile_image", performerObject)
                    )
                    performersList.add(performer)
                }

                _event.value = Event(
                    event = eventList,
                    comments = commentsList,
                    performers = performersList
                )

                println(eventList)
                println(commentsList)
                println(performersList)

            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }
     */

    private fun parsejson(key: String, jsonObject: JsonObject): String? {
        return if (jsonObject.get(key).isJsonNull) null else jsonObject.get(key)?.asString
    }
}

class WelcomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}