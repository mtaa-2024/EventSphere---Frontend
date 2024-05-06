package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.DateInput
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.LocationData
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.apiCalls
import java.io.IOException
import java.util.Calendar

class CreateEventViewModel(private val viewModel: MainViewModel, private  val initializeData: Event) : ViewModel() {
    private val _eventData = MutableLiveData<Event>()
    val eventData: LiveData<Event> = _eventData
    val estimatedEnd = MutableLiveData<DateInput>()
    private val initializeDate: DateInput
    val eventLocation = MutableLiveData<LocationData>()
    val performers = mutableListOf<User>()

    init {
        val mCalendar = Calendar.getInstance()
        initializeDate = DateInput(
            year = mCalendar.get(Calendar.YEAR),
            month = mCalendar.get(Calendar.MONTH) + 1,
            day = mCalendar.get(Calendar.DAY_OF_MONTH),
            hours = mCalendar.get(Calendar.HOUR_OF_DAY),
            minutes = mCalendar.get(Calendar.MINUTE)
        )
        estimatedEnd.value = initializeDate.copy()
        _eventData.value = initializeData.copy(
            ownerId = viewModel.loggedUser.value?.id!!,
            estimatedEnd = formatLocalDate()
        )
    }

    private fun formatDate(): String {
        return "${estimatedEnd.value?.year}-${estimatedEnd.value?.month}-${estimatedEnd.value?.day} ${estimatedEnd.value?.hours}:${estimatedEnd.value?.minutes}:00"
    }

    private fun formatLocalDate(): String {
        return "${estimatedEnd.value?.day}/${estimatedEnd.value?.month}/${estimatedEnd.value?.year} ${estimatedEnd.value?.hours}:${estimatedEnd.value?.minutes}"
    }

    fun updateLocation(input: LocationData) {
        eventLocation.value = input.copy()
    }

    fun onUpdateCategory(id: Int) {
        _eventData.value = _eventData.value?.copy(
            category = id
        )

    }

    fun updateDescription(description: String) {
        _eventData.value = _eventData.value?.copy(
            description = description
        )
        _eventData.value?.description = description
    }

    fun updateTitle(title: String) {
        _eventData.value = _eventData.value?.copy(
            title = title
        )
    }

    fun removePerformer(selectedPerformer: User?) {
        performers.remove(selectedPerformer)
    }

    fun addPerformer(friend: User) {
        if (!performers.contains(friend))
            performers.add(friend)
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

    suspend fun createEvent(): Pair<Boolean, String> {
        if (_eventData.value?.title == initializeData.title)
            return Pair(false, "Title is not initialized")
        if (_eventData.value?.description == initializeData.description)
            return Pair(false, "Description is not initialized")
        if (_eventData.value?.category == 0)
            return Pair(false, "Please select category of event")
        if (!eventLocation.isInitialized)
            return Pair(false, "Please select location")

        if (
            estimatedEnd.value?.year!! <= initializeDate.year &&
            estimatedEnd.value?.month!! <= initializeDate.month &&
            estimatedEnd.value?.day!! <= initializeDate.day &&
            estimatedEnd.value?.hours!! <= initializeDate.hours &&
            estimatedEnd.value?.minutes!! <= initializeDate.minutes
            )
            return Pair(false, "Please select valid date")
        if (eventLocation.isInitialized) {
            _eventData.value?.location = eventLocation.value?.address.toString()
            _eventData.value?.latitude = eventLocation.value?.latitude!!
            _eventData.value?.longitude = eventLocation.value?.longitude!!
        }
        if (performers.isEmpty())
            _eventData.value?.performers = null
        else
            _eventData.value?.performers = performers
        _eventData.value?.estimatedEnd = formatDate()

        if (eventData.isInitialized) {
            try {
                apiCalls.createEvent(eventData.value!!)
                return Pair(true, "")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return Pair(false, "ALL DONE")
    }
}

class CreateEventViewModelFactory(private val mainViewModel: MainViewModel, private val eventData: Event) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            return CreateEventViewModel(mainViewModel, eventData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}