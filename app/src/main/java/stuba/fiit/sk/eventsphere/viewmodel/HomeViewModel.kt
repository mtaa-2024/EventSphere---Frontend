package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.BannerStruct
import stuba.fiit.sk.eventsphere.model.CategorySelectStates
import stuba.fiit.sk.eventsphere.model.EventSelectStates
import stuba.fiit.sk.eventsphere.model.Events
import stuba.fiit.sk.eventsphere.model.FriendList

class HomeViewModel(viewModel: MainViewModel) : ViewModel() {
    private val viewModel = viewModel

    private val _categories = MutableLiveData<CategorySelectStates>()
    val categories: LiveData<CategorySelectStates>
        get() = _categories

    private val _eventSelectedStates = MutableLiveData<EventSelectStates>()
    val eventSelectStates: LiveData<EventSelectStates>
        get() = _eventSelectedStates


    private val _events = MutableLiveData<Events>()
    val events: LiveData<Events>
        get() = _events

    init {
        viewModelScope.launch {
            getUpcoming()
        }

        _eventSelectedStates.value = EventSelectStates(
            upcoming = true,
            attending = false,
            invited = false
        )

        _categories.value = CategorySelectStates(
            education = false,
            music = false,
            art = false,
            food = false,
            sport = false
        )
    }

    fun onUpdateFilter(input: String) {
        viewModelScope.launch {
            searchForEvents(input)
        }
    }

    private suspend fun searchForEvents(input: String) {
        try {
            val fetchedJson = apiService.searchEvents(input)
            val eventList = mutableListOf<BannerStruct>()
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
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
            }

            val upcomingEvents = Events(
                events = eventList
            )
            _events.value = upcomingEvents

        } catch (e: Exception) {
            return
        }
    }

    private suspend fun getUpcoming() {
        try {
            val fetchedJson = apiService.getUpcoming(
                _categories.value?.education ?: false,
                _categories.value?.music ?: false,
                _categories.value?.food ?: false,
                _categories.value?.art ?: false,
                _categories.value?.sport ?: false
            )
            val eventList = mutableListOf<BannerStruct>()
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
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
            }
            val upcomingEvents = Events(
                    events = eventList
            )
            _events.value = upcomingEvents
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    private suspend fun getAttending() {
        if ((viewModel.loggedUser.value?.id ?: 0) == 0)
            return
        try {
            val fetchedJson = apiService.getAttending(viewModel.loggedUser.value?.id)
            val eventList = mutableListOf<BannerStruct>()
            if (fetchedJson.get("result").asBoolean) {
                val eventArray = fetchedJson.getAsJsonArray("events").asJsonArray
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
            }
            val upcomingEvents = Events(
                events = eventList
            )
            _events.value = upcomingEvents
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

    private suspend fun getInvited() {
        try {

        } catch (e: Exception) {
            println("Error: $e")
        }
    }


    fun onUpcomingSelect() {
        _eventSelectedStates.value?.upcoming = true
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = false
        viewModelScope.launch{
            getUpcoming()
        }
    }
    fun onAttendingSelect() {
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = true
        _eventSelectedStates.value?.invited = false
        viewModelScope.launch{
            getAttending()
        }
    }
    fun onInvitedSelect() {
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = true
        viewModelScope.launch{
            getInvited()
        }
    }

    fun onClickEducation(value: Boolean) {
        _categories.value?.education = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        }
    }

    fun onClickMusic(value: Boolean) {
        _categories.value?.music = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        }
    }

    fun onClickArt(value: Boolean) {
        _categories.value?.art = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        }
    }

    fun onClickFood(value: Boolean) {
        _categories.value?.food = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        }
    }

    fun onClickSport(value: Boolean) {
        _categories.value?.sport = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        }
    }
}

class HomeViewModelFactory(private val viewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(viewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}