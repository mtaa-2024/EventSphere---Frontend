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
import stuba.fiit.sk.eventsphere.model.User

class EventViewModel(id: Int) : ViewModel() {
    private val _event = MutableLiveData<EventView>()
    val event: LiveData<EventView> = _event
    init {
        viewModelScope.launch{
            getEventData(id)
        }
    }

    suspend fun getEventData(id: Int) {
        try {
            val fetchedJson = apiService.getEvent(id)
            println(fetchedJson)
            val eventObject = fetchedJson.getAsJsonArray("event").get(0).asJsonObject
            val event = EventView(
                title = eventObject.get("title")?.asString,
                description = eventObject.get("description")?.asString,
                location = eventObject.get("location")?.asString,
                estimated_end = eventObject.get("estimated_end")?.asString,
                owner_firstname = eventObject.get("firstname")?.asString,
                owner_lastname = eventObject.get("lastname")?.asString,
                owner_picture = eventObject.get("profile_picture")?.asString
            )
            _event.value = event
            println(_event.value)
        } catch (e: Exception) {
            println("Error: $e")
        }
    }

}
data class EventView (
    var title: String?,
    var description: String?,
    var location: String?,
    var estimated_end: String?,
    var owner_firstname: String?,
    var owner_lastname: String?,
    var owner_picture: String?
)

class EventViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}