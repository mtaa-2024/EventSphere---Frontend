package stuba.fiit.sk.eventsphere.viewmodel
import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.EventStruct
import stuba.fiit.sk.eventsphere.model.Upcoming
import stuba.fiit.sk.eventsphere.model.UpcomingStruct
import stuba.fiit.sk.eventsphere.model.User

class MainViewModel() : ViewModel() {
    private val _loggedUser = MutableLiveData<User>()
    val loggedUser: LiveData<User> = _loggedUser

    private val _upcoming = MutableLiveData<Upcoming>()
    val upcoming: LiveData<Upcoming> = _upcoming

    suspend fun authenticateUser(username: String, password: String): Boolean {
        if (username != "" && password != "") {
            try {
                val fetchedJson = apiService.getUser(username, password)
                if (fetchedJson.get("result").asBoolean) {
                    val userObject = fetchedJson.getAsJsonArray("user")[0].asJsonObject
                    val loggedUser = User(
                        id = userObject.get("id").asInt,
                        username = userObject.get("username").asString,
                        email = userObject.get("email").asString,
                        firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                        lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                        profile_image = if (userObject.get("profile_image").isJsonNull) { null } else { userObject.get("profile_image")?.asString },
                    )
                    _loggedUser.value = loggedUser
                    println(loggedUser)
                    return true
                }
            } catch (e: Exception) {
                println("Error: $e")
                return false
            }
        }
        return false
    }
    suspend fun obtainFriends() {
        if (_loggedUser.isInitialized) {
            val id = _loggedUser.value?.id
            try {
                val fetchedJson = apiService.getFriends(id)
                println(fetchedJson)
            } catch (e: Exception) {
                println("Error: $e")
            }
        }
    }
    suspend fun getUpcoming() {
        try {
            val fetchedJson = apiService.getUpcoming()
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
                val eventList = mutableListOf<UpcomingStruct>()
                eventArray.forEach { eventElement ->
                    val eventObject = eventElement.asJsonObject
                    val event = UpcomingStruct(
                        id = eventObject.get("id").asInt,
                        title = if (eventObject.get("title").isJsonNull) { null } else { eventObject.get("title")?.asString },
                        location = if (eventObject.get("location").isJsonNull) { null } else { eventObject.get("location")?.asString },
                        date = if (eventObject.get("estimated_end").isJsonNull) { null } else { eventObject.get("estimated_end")?.asString }
                    )
                    eventList.add(event)
                }
                val upcomingEvents = Upcoming (
                    events = eventList
                )
                _upcoming.value = upcomingEvents
            }
            println(_upcoming.value)
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}