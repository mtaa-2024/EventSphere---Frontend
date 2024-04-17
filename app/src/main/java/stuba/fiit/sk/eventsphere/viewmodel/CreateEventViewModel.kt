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
import stuba.fiit.sk.eventsphere.model.DateStructure
import stuba.fiit.sk.eventsphere.model.EventCreate
import stuba.fiit.sk.eventsphere.model.FriendsView
import stuba.fiit.sk.eventsphere.model.ListFriendsView
import stuba.fiit.sk.eventsphere.model.PerformerStruct
import stuba.fiit.sk.eventsphere.model.PerformersView
import stuba.fiit.sk.eventsphere.model.User

class CreateEventViewModel(viewModel: MainViewModel) : ViewModel() {

    private val creatorId = viewModel.loggedUser.value?.id

    private val _eventData = MutableLiveData<EventCreate>()
    val eventData: LiveData<EventCreate> = _eventData

    private val _date = MutableLiveData<DateStructure>()
    val date: LiveData<DateStructure> = _date

    val performerList = mutableListOf<FriendsView>()

    val calendar = Calendar.getInstance()

    private val _addPerformerState = MutableLiveData<AddPerformerState>()
    val addPerformerState: LiveData<AddPerformerState> = _addPerformerState

    private val _friends = MutableLiveData<ListFriendsView>()
    val friends: LiveData<ListFriendsView> = _friends

    init {
        viewModelScope.launch {
            getFriends()
        }

        _eventData.value = EventCreate (
            title = "Title",
            description = "Description",
            location = "Location",
            user_id = viewModel.loggedUser.value?.id,
            estimated_end = "Start date",
        )

        _date.value = DateStructure(
            day = calendar[Calendar.DAY_OF_MONTH],
            month = calendar[Calendar.MONTH],
            year = calendar[Calendar.YEAR],
            hour = calendar[Calendar.HOUR_OF_DAY],
            minutes = calendar[Calendar.MINUTE]
        )

        _addPerformerState.value = AddPerformerState(
            friend = true,
            input = false
        )
    }

    fun updateTitle(input: String) {
        _eventData.value?.title = input
    }
    fun updateDescription(input: String) {
        _eventData.value?.description = input
    }
    fun updateLocation(input: String) {
        _eventData.value?.location = input
    }
    fun updateEstimatedEnd(input: String) {
        _eventData.value?.estimated_end = input
    }
    fun addPerformer(friend: FriendsView) {
        performerList.add(friend)
    }

    fun removePerformer(id: Int?, firstname: String?, lastname: String?) {
        if (id != null) {
            performerList.removeIf { it.id == id }
        } else {
            performerList.removeIf { it.firstname == firstname && it.lastname == lastname }
        }
    }

    suspend fun getFriends() {
        try {
            val fetchedJson = apiService.getFriends(creatorId)
            val friendsList = mutableListOf<FriendsView>()
            if (fetchedJson.get("result").asBoolean) {
                val friendsArray = fetchedJson.getAsJsonArray("friends").asJsonArray
                friendsArray.forEach { friendsElement ->
                    val friendsObject = friendsElement.asJsonObject
                    val friendsView = FriendsView(
                        id = if (friendsObject.get("id").isJsonNull) null else friendsObject.get("id").asInt,
                        firstname = if (friendsObject.get("firstname").isJsonNull) null else friendsObject.get(
                            "firstname"
                        ).asString,
                        lastname = if (friendsObject.get("lastname").isJsonNull) null else friendsObject.get(
                            "lastname"
                        ).asString,
                        profile_picture = if (friendsObject.get("profile_image").isJsonNull) null else friendsObject.get(
                            "profile_image"
                        ).asString,
                    )
                    friendsList.add(friendsView)
                }
                val friends = ListFriendsView(
                    listFriends = friendsList
                )
                _friends.value = friends
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun createEvent(): Int {
        try {
            val jsonBody = JsonObject()
            jsonBody.addProperty("title", _eventData.value?.title)
            jsonBody.addProperty("user_id", _eventData.value?.user_id)
            jsonBody.addProperty("description", _eventData.value?.description)
            jsonBody.addProperty("location", _eventData.value?.location)
            jsonBody.addProperty("estimated_end", _eventData.value?.estimated_end)
            val performersArray = JsonArray()


            performerList.forEach { performer ->
                val performerObject = JsonObject()
                performerObject.addProperty("id", performer.id)
                performerObject.addProperty("firstname", performer.firstname)
                performerObject.addProperty("lastname", performer.lastname)
                performerObject.addProperty("profile_image", performer.profile_picture)

                performersArray.add(performerObject)
            }
            jsonBody.add("performers", performersArray)

            println(jsonBody)

            val fetchedJson = apiService.createEvent(jsonBody)

            if (fetchedJson.get("result").asBoolean) {
                return fetchedJson.get("id").asInt
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
        return -1
    }

    fun onFriendSelect() {
        _addPerformerState.value?.friend = true
        _addPerformerState.value?.input = false
    }

    fun onInputSelect() {
        _addPerformerState.value?.friend = false
        _addPerformerState.value?.input = true
    }
}

data class AddPerformerState(
    var friend: Boolean,
    var input: Boolean
)

class CreateEventViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            return CreateEventViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}