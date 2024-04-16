package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.BannerStruct
import stuba.fiit.sk.eventsphere.model.EventCreate
import stuba.fiit.sk.eventsphere.model.PerformerStruct

class CreateEventViewModel() : ViewModel() {
    private val _eventData = MutableLiveData<EventCreate>()
    val eventData: LiveData<EventCreate> = _eventData

    private val performerList = mutableListOf<PerformerStruct>()

    init {
        _eventData.value = EventCreate (
            title = "Title",
            description = "Description",
            location = "Location",
            estimated_end = "19.3.2024",
            performers = null
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
    fun addPerformer(id: Int?, firstname: String?, lastname: String?) {
        if (id != null) {
            val performer = PerformerStruct(
                    id = id,
                    firstname = null,
                    lastname = null,
                    profile_image = null
            )
            performerList.add(performer)
        } else {
            val performer = PerformerStruct(
                id = null,
                firstname = firstname,
                lastname = lastname,
                profile_image = null
            )
            performerList.add(performer)
        }
    }

    suspend fun createEvent() {
        try {
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}


/*
data class EventCreate (
    var title: String?,
    var description: String?,
    var location: String?,
    var estimated_end: String?,
    var performers: List<PerformerStruct>?
)
*/
class CreateEventViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            return CreateEventViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}