package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.apiCalls

data class EventSelectStates (
    var upcoming: Boolean = true,
    var attending: Boolean = false,
    var invited: Boolean = false
)
data class CategorySelectStates (
    var education: Boolean = false,
    var music: Boolean = false,
    var art: Boolean = false,
    var food: Boolean = false,
    var sport: Boolean = false
)

data class Events (
    var events: MutableList<Event>?
)

class HomeViewModel(viewModel: MainViewModel) : ViewModel() {
    private val viewModel = viewModel

    private val _categories = MutableLiveData<CategorySelectStates>()
    val categories: LiveData<CategorySelectStates>
        get() = _categories

    private val _eventSelectedStates = MutableLiveData<EventSelectStates>()
    val eventSelectStates: LiveData<EventSelectStates>
        get() = _eventSelectedStates

    private val _events = MutableLiveData<Events>()
    val events: LiveData<Events> = _events

    init {
        _categories.value = CategorySelectStates ()
        _eventSelectedStates.value = EventSelectStates()
        _events.value = Events (
            events = null
        )
        viewModelScope.launch {
            getUpcoming()
        }
    }


    fun onUpcomingSelect() {
        _eventSelectedStates.value?.upcoming = true
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = false
        viewModelScope.launch {
            getUpcoming()
        }

    }
    fun onAttendingSelect() {
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = true
        _eventSelectedStates.value?.invited = false
        viewModelScope.launch {
            getAttending()
        }
    }


    fun onInvitedSelect() {
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.attending = false
        _eventSelectedStates.value?.invited = true

    }

    fun onClickEducation(value: Boolean) {
        _categories.value?.education = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        } else {
            viewModelScope.launch {
                getAttending()
            }
        }
    }

    fun onClickMusic(value: Boolean) {
        _categories.value?.music = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        } else {
            viewModelScope.launch {
                getAttending()
            }
        }
    }

    fun onClickArt(value: Boolean) {
        _categories.value?.art = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        } else {
            viewModelScope.launch {
                getAttending()
            }
        }
    }

    fun onClickFood(value: Boolean) {
        _categories.value?.food = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        } else {
            viewModelScope.launch {
                getAttending()
            }
        }
    }

    fun onClickSport(value: Boolean) {
        _categories.value?.sport = value
        if (eventSelectStates.value?.upcoming == true) {
            viewModelScope.launch {
                getUpcoming()
            }
        } else {
            viewModelScope.launch {
                getAttending()
            }
        }
    }

    private suspend fun getUpcoming() {
        val events: MutableList<Event>? = apiCalls.getUpcomingEvents(categories.value!!)
        _events.value = _events.value?.copy(
            events = events
        )
    }

    private suspend fun getAttending() {
        val events: MutableList<Event>? = apiCalls.getAttendingEvents(categories.value!!, viewModel.loggedUser.value!!)
        _events.value = _events.value?.copy(
            events = events
        )
    }

    suspend fun onUpdateFilter(filter: String) {
        if (eventSelectStates.value?.upcoming == true) {
            val events: MutableList<Event>? = apiCalls.searchEvents(categories.value!!, filter)
            _events.value = _events.value?.copy(
                events = events
            )
        } else {
            val events: MutableList<Event>? = apiCalls.searchEventsAttending(categories.value!!, filter, viewModel.loggedUser.value!!)
            _events.value = _events.value?.copy(
                events = events
            )
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