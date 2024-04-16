package stuba.fiit.sk.eventsphere.viewmodel

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.BannerStruct
import stuba.fiit.sk.eventsphere.model.Category
import stuba.fiit.sk.eventsphere.model.Events

class EventCenterViewModel(viewModel: MainViewModel) : ViewModel() {
    private val _eventSelectedStates = MutableLiveData<EventSelectStatesCenter>()
    val eventSelectStates: LiveData<EventSelectStatesCenter> = _eventSelectedStates

    private val _upcoming = MutableLiveData<Events>()
    val upcoming: LiveData<Events>
        get() = _upcoming

    private val _expired = MutableLiveData<Events>()
    val expired: LiveData<Events>
        get() = _expired

    init {

        _eventSelectedStates.value = EventSelectStatesCenter (
            upcoming = true,
            expired = false
        )

        viewModelScope.launch {
            getUpcoming(viewModel)
            getExpired(viewModel)
        }
    }

    fun onUpcomingSelect(viewModel: MainViewModel) {
        viewModelScope.launch{
            getUpcoming(viewModel)
        }
        _eventSelectedStates.value?.upcoming = true
        _eventSelectedStates.value?.expired = false
    }
    fun onExpiredSelect(viewModel: MainViewModel) {
        viewModelScope.launch{
            getExpired(viewModel)
        }
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.expired = true
    }

    suspend fun getUpcoming(viewModel: MainViewModel) {
        try {
            val fetchedJson = apiService.getUpcomingOwner(viewModel.loggedUser.value?.id)
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
                val eventList = mutableListOf<BannerStruct>()
                eventArray.forEach { eventElement ->
                    val eventObject = eventElement.asJsonObject
                    val event = BannerStruct(
                        id = eventObject.get("id").asInt,
                        title = if (eventObject.get("title").isJsonNull) {
                            null
                        } else {
                            eventObject.get("title")?.asString
                        },
                        location = if (eventObject.get("location").isJsonNull) {
                            null
                        } else {
                            eventObject.get("location")?.asString
                        },
                        date = if (eventObject.get("estimated_end").isJsonNull) {
                            null
                        } else {
                            eventObject.get("estimated_end")?.asString
                        }
                    )
                    eventList.add(event)
                }
                val upcomingEvents = Events(
                    events = eventList
                )
                _upcoming.value = upcomingEvents
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    suspend fun getExpired(viewModel: MainViewModel) {
        try {
            val fetchedJson = apiService.getExpiredOwner(viewModel.loggedUser.value?.id)
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
                val eventList = mutableListOf<BannerStruct>()
                eventArray.forEach { eventElement ->
                    val eventObject = eventElement.asJsonObject
                    val event = BannerStruct(
                        id = eventObject.get("id").asInt,
                        title = if (eventObject.get("title").isJsonNull) {
                            null
                        } else {
                            eventObject.get("title")?.asString
                        },
                        location = if (eventObject.get("location").isJsonNull) {
                            null
                        } else {
                            eventObject.get("location")?.asString
                        },
                        date = if (eventObject.get("estimated_end").isJsonNull) {
                            null
                        } else {
                            eventObject.get("estimated_end")?.asString
                        }
                    )
                    eventList.add(event)
                }
                val upcomingEvents = Events(
                    events = eventList
                )
                _expired.value = upcomingEvents
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}

data class EventSelectStatesCenter (
    var upcoming: Boolean,
    var expired: Boolean
)
class EventCenterViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventCenterViewModel::class.java)) {
            return EventCenterViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}