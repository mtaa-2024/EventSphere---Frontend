package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.BannerStruct
import stuba.fiit.sk.eventsphere.model.Category
import stuba.fiit.sk.eventsphere.model.Events

class HomeViewModel() : ViewModel() {
    private val _categories = MutableLiveData<Category>()
    val categories: LiveData<Category> = _categories

    private val _eventSelectedStates = MutableLiveData<EventSelectStates>()
    val eventSelectStates: LiveData<EventSelectStates> = _eventSelectedStates

    private val _events = MutableLiveData<Events>()
    val events: LiveData<Events>
        get() = _events

    init {
        viewModelScope.launch{
            getUpcoming()
        }

        _eventSelectedStates.value = EventSelectStates (
            upcoming = true,
            attending = false,
            invited = false
        )

        _categories.value = Category(
            education = false,
            music = false,
            art = false,
            food = false,
            sport = false
        )
    }

    private suspend fun getUpcoming() {
        try {
            val fetchedJson = apiService.getUpcoming()
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
                _events.value = upcomingEvents
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
        println(_events.value)
    }

    private suspend fun getAttending(viewModel: MainViewModel) {
        try {
            val fetchedJson = apiService.getAttending(viewModel.loggedUser.value?.id)
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
                _events.value = upcomingEvents
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
        println(_events.value)
    }

    private suspend fun getInvited(viewModel: MainViewModel) {
        try {
            val fetchedJson = apiService.getUpcoming()
            println(fetchedJson)
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
                _events.value = upcomingEvents
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    fun onUpcomingSelect() {
        viewModelScope.launch{
            getUpcoming()
        }
        _eventSelectedStates.value?.upcoming = true
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = false
    }
    fun onAttendingSelect(viewModel: MainViewModel) {
        viewModelScope.launch{
            getAttending(viewModel)
        }
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = true
        _eventSelectedStates.value?.invited = false
    }
    fun onInvitedSelect(viewModel: MainViewModel) {
        viewModelScope.launch{
            getInvited(viewModel)
        }
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = true
    }

    fun onClickEducation(value: Boolean) {
        println(value)
        _categories.value?.education = value
    }

    fun onClickMusic(value: Boolean) {
        _categories.value?.music = value
    }

    fun onClickArt(value: Boolean) {
        _categories.value?.art = value
    }

    fun onClickFood(value: Boolean) {
        _categories.value?.food = value
    }

    fun onClickSport(value: Boolean) {
        _categories.value?.sport = value
    }
}

data class EventSelectStates (
    var upcoming: Boolean,
    var attending: Boolean,
    var invited: Boolean
)

class HomeViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}