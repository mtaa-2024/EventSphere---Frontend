package stuba.fiit.sk.eventsphere.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.DateStructure
import stuba.fiit.sk.eventsphere.model.EventCreate
import stuba.fiit.sk.eventsphere.model.PerformerStruct

class CreateEventViewModel(viewModel: MainViewModel) : ViewModel() {
    private val _eventData = MutableLiveData<EventCreate>()
    val eventData: LiveData<EventCreate> = _eventData

    private val _date = MutableLiveData<DateStructure>()
    val date: LiveData<DateStructure> = _date

    private val performerList = mutableListOf<PerformerStruct>()
    val calendar = Calendar.getInstance()

    init {
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
        println(input)
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

    fun removePerformer(id: Int?, firstname: String?, lastname: String?) {
        if (id != null) {
            performerList.removeIf { it.id == id }
        } else {
            performerList.removeIf { it.firstname == firstname && it.lastname == lastname }
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
                performerObject.addProperty("profile_image", performer.profile_image)

                performersArray.add(performerObject)
            }
            jsonBody.add("performers", performersArray)

            val fetchedJson = apiService.createEvent(jsonBody)

            if (fetchedJson.get("result").asBoolean) {
                return fetchedJson.get("id").asInt
            }

        } catch (e: Exception) {
            println("Error: $e")
        }
        return -1
    }
}

class CreateEventViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateEventViewModel::class.java)) {
            return CreateEventViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}