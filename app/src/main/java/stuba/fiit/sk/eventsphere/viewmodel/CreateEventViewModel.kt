package stuba.fiit.sk.eventsphere.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.AddPerformerState
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.EventInput
import stuba.fiit.sk.eventsphere.model.FriendPerformer
import stuba.fiit.sk.eventsphere.model.LocationData

class CreateEventViewModel(viewModel: MainViewModel) : ViewModel() {
    private val creatorId = viewModel.loggedUser.value?.id

    private val _event = MutableLiveData<EventInput>()
    val event: LiveData<EventInput> = _event

    private val performersList: MutableList<FriendPerformer> = mutableListOf()
    val friendsList: MutableList<FriendPerformer> = mutableListOf()

    var selectedCategory: String = ""

    fun updateSelectedCategory(input: String) {
        selectedCategory = input
    }

    init {
        viewModelScope.launch {
            getFriends()
        }
        val calendar = Calendar.getInstance()

        _event.value = EventInput (
            title = "Title",
            description = "Description",
            location = LocationData (
                address = null,
                latitude = 0.0,
                longitude = 0.0,
            ),
            user_id = viewModel.loggedUser.value?.id ?: -1,
            estimated_end = DateInput (
                day = calendar[Calendar.DAY_OF_MONTH],
                month = calendar[Calendar.MONTH],
                year = calendar[Calendar.YEAR],
                hour = calendar[Calendar.HOUR_OF_DAY],
                minutes = calendar[Calendar.MINUTE]
            ),
            performers = performersList

        )
    }

    fun updateTitle(input: String) {
        _event.value?.title = input
    }
    fun updateDescription(input: String) {
        _event.value?.description = input
    }
    fun updateLocation(input: LocationData) {
        _event.value?.location = input
    }
    fun updateEstimatedEnd(input: DateInput) {
        val currentEvent = _event.value ?: return
        val updatedEvent = currentEvent.copy(estimated_end = input)
        _event.value = updatedEvent
    }
    fun addPerformer(friend: FriendPerformer) {
        performersList.add(friend)
        friendsList.remove(friend)
    }

    fun removePerformer(performer: FriendPerformer?) {
        if (performer != null) {
            performersList.remove(performer)
            if ((performer.id ?: -1) != -1)
                friendsList.add(performer)
        }
    }


    suspend fun getFriends() {
        try {
            val fetchedJson = apiService.getFriends(creatorId)
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
                    friendsList.add(friendsView)
                }
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun createEvent(): Int {
        try {
            val categoryId = if (selectedCategory == "Music") 1 else if (selectedCategory == "Education") 2 else if (selectedCategory == "Food") 3 else if (selectedCategory == "Sport") 4 else  5

            val jsonBody = JsonObject()
            jsonBody.addProperty("title", _event.value?.title)
            jsonBody.addProperty("user_id", _event.value?.user_id)
            jsonBody.addProperty("description", _event.value?.description)
            jsonBody.addProperty("location", _event.value?.location?.address)
            jsonBody.addProperty("latitude", _event.value?.location?.latitude)
            jsonBody.addProperty("longitude", _event.value?.location?.longitude)
            jsonBody.addProperty("category", categoryId)

            val timestamp = "${_event.value?.estimated_end?.day}.${_event.value?.estimated_end?.month}.${_event.value?.estimated_end?.year} ${_event.value?.estimated_end?.hour}:${_event.value?.estimated_end?.minutes}"
            jsonBody.addProperty("estimated_end", timestamp)


            val performersArray = JsonArray()


            performersList.forEach { performer ->
                val performerObject = JsonObject()
                performerObject.addProperty("id", performer.id)
                performersArray.add(performerObject)
            }
            jsonBody.add("performers", performersArray)


            val fetchedJson = apiService.createEvent(jsonBody)
            if (fetchedJson.get("result").asBoolean) {
                return fetchedJson.get("id").asInt
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
        return -1
    }
}

class CreateEventViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            return CreateEventViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}