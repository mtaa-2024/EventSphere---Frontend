package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.User

class FriendsViewModel(id:Int) : ViewModel() {
    private val _friend = MutableLiveData<User>()
    val friend: LiveData<User> = _friend

    init {
        viewModelScope.launch {
            getFriend(id)
        }
    }

    suspend fun getFriend(id: Int){
        try {
            val fetchedJson = apiService.getUserData(id)
            if (fetchedJson.get("result").asBoolean) {
                val userObject = fetchedJson.getAsJsonArray("user")[0].asJsonObject
                val friend = User(
                    id = userObject.get("id").asInt,
                    username = userObject.get("username").asString,
                    email = userObject.get("email").asString,
                    firstname = if (userObject.get("firstname").isJsonNull) {
                        null
                    } else {
                        userObject.get("firstname")?.asString
                    },
                    lastname = if (userObject.get("lastname").isJsonNull) {
                        null
                    } else {
                        userObject.get("lastname")?.asString
                    },
                    profile_image = if (userObject.get("profile_image").isJsonNull) {
                        null
                    } else {
                        userObject.get("profile_image")?.asString
                    },
                )
                _friend.value = friend
            }
        } catch (e: Exception) {
                println("Error: $e")

        }
    }
}

class FriendsViewModelFactory(private val id:Int ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)) {
            return FriendsViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}