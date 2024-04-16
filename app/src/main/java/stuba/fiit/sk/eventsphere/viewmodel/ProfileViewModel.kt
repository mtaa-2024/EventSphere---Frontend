package stuba.fiit.sk.eventsphere.viewmodel

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.Events
import stuba.fiit.sk.eventsphere.model.User


class ProfileViewModel(id:Int) : ViewModel() {

    private val _friends = MutableLiveData<ListFriendsView>()
    val friends: LiveData<ListFriendsView> = _friends

    init {
        viewModelScope.launch {
            getProfileData(id)
        }
    }

    suspend fun getProfileData(id: Int) {
        try {
            val fetchedJson = apiService.getFriends(id)
            val friendsList = mutableListOf<FriendsView>()
            println(fetchedJson)

            if (fetchedJson.get("result").asBoolean) {
                val friendsArray = fetchedJson.getAsJsonArray("friends").asJsonArray
                println(friendsArray)
                friendsArray.forEach { friendsElement ->
                    val friendsObject = friendsElement.asJsonObject
                    val friendsView = FriendsView(
                        id = if (friendsObject.get("id").isJsonNull) null else friendsObject.get("id").asInt,
                        firstname = if (friendsObject.get("firstname").isJsonNull) null else friendsObject.get(
                            "firstname"
                        ).asString,
                        lastname = if (friendsObject.get("lastname").isJsonNull) null else friendsObject.get(
                            "lastname"
                        ).asString,
                        profile_picture = if (friendsObject.get("profile_image").isJsonNull) null else friendsObject.get(
                            "profile_image"
                        ).asString,
                    )
                    friendsList.add(friendsView)
                }
                val friends = ListFriendsView(
                    listFriends = friendsList
                )
                _friends.value = friends
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}
class ProfileViewModelFactory(private val id: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class FriendsView (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?
)

data class ListFriendsView (
    var listFriends: List<FriendsView>?
)