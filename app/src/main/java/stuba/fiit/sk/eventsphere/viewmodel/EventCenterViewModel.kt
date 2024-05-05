package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.apiCalls

data class EventSelectStatesCenter (
    var upcoming: Boolean,
    var expired: Boolean
)

class EventCenterViewModel(private val viewModel: MainViewModel) : ViewModel() {

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

        _upcoming.value = Events(null)
        _expired.value = Events(null)

        viewModelScope.launch {
            getUpcoming()
            getExpired()
        }
    }

    fun onUpcomingSelect() {
        _eventSelectedStates.value?.upcoming = true
        _eventSelectedStates.value?.expired = false
    }
    fun onExpiredSelect() {
        _eventSelectedStates.value?.upcoming = false
        _eventSelectedStates.value?.expired = true
    }

    suspend fun getUpcoming() {
        val events: MutableList<Event>? = apiCalls.getUpcomingUserEvents(viewModel.loggedUser.value!!)
        println(events)
        _upcoming.value = _upcoming.value?.copy(
            events = events
        )
    }

    suspend fun getExpired() {
        val events: MutableList<Event>? = apiCalls.getExpiredUserEvents(viewModel.loggedUser.value!!)
        _expired.value = _expired.value?.copy(
            events = events
        )
    }

}

class EventCenterViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventCenterViewModel::class.java)) {
            return EventCenterViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}