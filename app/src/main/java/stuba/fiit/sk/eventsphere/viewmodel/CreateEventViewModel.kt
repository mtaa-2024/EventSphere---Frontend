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

    var timestamp = "${_event.value?.estimated_end?.day}.${_event.value?.estimated_end?.month!! + 1}.${_event.value?.estimated_end?.year} ${_event.value?.estimated_end?.hour}:${_event.value?.estimated_end?.minutes}"

    fun updateTimestamp() {
        timestamp = "${_event.value?.estimated_end?.day}.${_event.value?.estimated_end?.month!! + 1}.${_event.value?.estimated_end?.year} ${_event.value?.estimated_end?.hour}:${_event.value?.estimated_end?.minutes}"
    }

    private val calendar: Calendar = Calendar.getInstance()
    var date = DateInput (
        day = calendar[Calendar.DAY_OF_MONTH],
        month = calendar[Calendar.MONTH],
        year = calendar[Calendar.YEAR],
        hour = calendar[Calendar.HOUR_OF_DAY],
        minutes = calendar[Calendar.MINUTE]
    )

    val actualDate = DateInput (
        day = calendar[Calendar.DAY_OF_MONTH],
        month = calendar[Calendar.MONTH],
        year = calendar[Calendar.YEAR],
        hour = calendar[Calendar.HOUR_OF_DAY],
        minutes = calendar[Calendar.MINUTE]
    )

    init {
        viewModelScope.launch {
            getFriends()
        }

        _event.value = EventInput (
            title = "Title",
            description = "Description",
            location = LocationData (
                address = "",
                latitude = 0.0,
                longitude = 0.0,
            ),
            user_id = viewModel.loggedUser.value?.id ?: -1,
            estimated_end = date,
            performers = performersList,
            category = 0
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

    suspend fun createEvent(): Pair<Boolean, String> {

        if (_event.value?.title == "" || _event.value?.title == "Title") {
            return Pair(false, "Title must be initialized")
        }
        if (_event.value?.description == "" || _event.value?.description == "Description") {
            return Pair(false, "Description must be initialized")
        }

        /*
        if (_event.value?.location == LocationData (
                address = null,
                latitude = 0.0,
                longitude = 0.0, )
            ) {
            return Pair(false, "Location must be initialized")
        }

         */


        if (_event.value?.estimated_end == actualDate) {
            return Pair(false, "Date must be initialized")
        }

        if (_event.value?.estimated_end?.day!! < actualDate.day && _event.value?.estimated_end?.month!! < actualDate.month && _event.value?.estimated_end?.year!! < actualDate.year && _event.value?.estimated_end?.hour!! < actualDate.hour && _event.value?.estimated_end?.minutes!! < actualDate.minutes) {
            return Pair(false, "Selected date is in past, select time in future")
        }

        if (_event.value?.category == 0) {
            return Pair(false, "Category must be initialized")
        }

        try {
            val jsonBody = JsonObject()
            jsonBody.addProperty("title", _event.value?.title)
            jsonBody.addProperty("user_id", _event.value?.user_id)
            jsonBody.addProperty("description", _event.value?.description)
            jsonBody.addProperty("location", _event.value?.location?.address)
            jsonBody.addProperty("latitude", _event.value?.location?.latitude)
            jsonBody.addProperty("longitude", _event.value?.location?.longitude)
            jsonBody.addProperty("category", _event.value?.category)

            timestamp = "${_event.value?.estimated_end?.day}.${_event.value?.estimated_end?.month!! + 1}.${_event.value?.estimated_end?.year} ${_event.value?.estimated_end?.hour}:${_event.value?.estimated_end?.minutes}"
            jsonBody.addProperty("estimated_end", timestamp)


            val performersArray = JsonArray()
            performersList.forEach { performer ->
                val performerObject = JsonObject()
                performerObject.addProperty("id", performer.id)
                performersArray.add(performerObject)
            }
            jsonBody.add("performers", performersArray)

            println(jsonBody)

            val fetchedJson = apiService.createEvent(jsonBody)
            if (fetchedJson.get("result").asBoolean)
                return Pair(true, "")
            else
                return Pair(false, "Error creating event")
        } catch (e: Exception) {
            println("Error: $e")
        }
        return Pair(false, "Unexpected error")
    }

    fun onUpdateCategory(id: Int) {
        _event.value?.category = id
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