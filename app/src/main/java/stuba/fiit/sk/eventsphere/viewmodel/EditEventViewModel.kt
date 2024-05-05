package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.LocationData
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.apiCalls

class EditEventViewModel (val event: Event, private val mainViewModel: MainViewModel) : ViewModel() {
    private val _eventData = MutableLiveData<Event>()
    val eventData: LiveData<Event> = _eventData

    val friendsList: MutableList<User>? = mainViewModel.friendsData.value?.friends
    val performersList: MutableList<User>? = event.performers

    val estimatedEnd = MutableLiveData<DateInput>()

    private fun formatDate(): String {
        return "${estimatedEnd.value?.year}-${estimatedEnd.value?.month}-${estimatedEnd.value?.day} ${estimatedEnd.value?.hours}:${estimatedEnd.value?.minutes}:00"
    }

    private fun formatLocalDate(): String {
        return "${estimatedEnd.value?.day}/${estimatedEnd.value?.month}/${estimatedEnd.value?.year} ${estimatedEnd.value?.hours}:${estimatedEnd.value?.minutes}"
    }

    init {
        _eventData.value = event.copy()
        performersList?.forEach { performer ->
            if (friendsList?.contains(performer) == true)
                friendsList.remove(performer)
        }

        estimatedEnd.value = DateInput(
            year = 0,
            month = 0,
            day = 0,
            hours = 0,
            minutes = 0
        )
    }

    suspend fun updateEvent(): Pair<Boolean, String> {
        val body = JsonObject()
        body.addProperty("id", eventData.value?.id.toString())
        if (eventData.value?.title != event.title)
            body.addProperty("title", eventData.value?.title)
        if (eventData.value?.description != event.description)
            body.addProperty("description", eventData.value?.description)
        if (eventData.value?.location != event.location)
            body.addProperty("location", eventData.value?.location)
        if (eventData.value?.category != event.category)
            body.addProperty("category", eventData.value?.category)
        if (eventData.value?.estimatedEnd != event.estimatedEnd)
            body.addProperty("estimated_end", formatDate())
        if (eventData.value?.latitude != event.latitude)
            body.addProperty("latitude", eventData.value?.latitude)
        if (eventData.value?.longitude != event.longitude)
            body.addProperty("longitude", eventData.value?.longitude)

        eventData.value?.performers?.forEach { performer ->
            val performerObject = JsonObject()
            performerObject.addProperty("user_id", performer.id.toString())
            body.add("performers", performerObject)
        }

        val (result, message) = apiCalls.updateEvent(body)
        return Pair(result, message)
    }

    fun updateDescription(description: String) {
        _eventData.value = _eventData.value?.copy(description = description)
    }

    fun updateTitle(title: String) {
        _eventData.value = _eventData.value?.copy(title = title)
    }

    fun addPerformer(friend: User) {
        performersList?.add(friend)
        friendsList?.remove(friend)
    }

    fun removePerformer(selectedPerformer: User) {
        performersList?.remove(selectedPerformer)
        friendsList?.add(selectedPerformer)
    }

    fun updateLocation(input: LocationData) {
        _eventData.value = _eventData.value?.copy(
            location = input.address,
            latitude = input.latitude,
            longitude = input.longitude
        )
    }

    fun updateCategory(id: Int) {
        _eventData.value = _eventData.value?.copy(category = id)
    }

    fun updateTime(mHours: Int, mMinutes: Int) {
        estimatedEnd.value = estimatedEnd.value?.copy(
            hours = mHours,
            minutes = mMinutes
        )
        _eventData.value = _eventData.value?.copy(
            estimatedEnd = formatLocalDate()
        )
    }

    fun updateDate(mYear: Int, mMonth: Int, mDay: Int) {
        estimatedEnd.value = estimatedEnd.value?.copy(
            year = mYear,
            month = mMonth + 1,
            day = mDay
        )
    }
}

class EditEventViewModelFactory(private val event: Event, private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditEventViewModel::class.java)) {
            return EditEventViewModel(event, mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
