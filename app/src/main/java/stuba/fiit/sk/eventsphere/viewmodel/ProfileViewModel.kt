package stuba.fiit.sk.eventsphere.viewmodel

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.Friend
import stuba.fiit.sk.eventsphere.model.FriendList

class ProfileViewModel(id:Int) : ViewModel() {

    private val _friends = MutableLiveData<FriendList>()
    val friends: LiveData<FriendList> = _friends

    init {
        viewModelScope.launch {
            getProfileData(id)
        }
    }

    private fun clearFriends() {
        _friends.value = FriendList(emptyList())
    }

    private suspend fun getProfileData(id: Int) {
        try {
            val fetchedJson = apiService.getFriends(id)
            val friendsList = mutableListOf<Friend>()

            if (fetchedJson.get("result").asBoolean) {

                clearFriends()

                val friendsArray = fetchedJson.getAsJsonArray("friends").asJsonArray
                friendsArray.forEach { friendsElement ->
                    val friendsObject = friendsElement.asJsonObject

                    var bitmap: ImageBitmap? = null

                    val imageArray = if (friendsObject.get("profile_image").isJsonNull) null else friendsObject.getAsJsonObject("profile_image").getAsJsonArray("data")
                    if (imageArray != null) {
                        val image = jsonArrayToByteArray(imageArray).decodeToString()
                        val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
                        val imageBitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
                        bitmap= imageBitMap.asImageBitmap()
                    }

                    val friendsView = Friend(
                        id = if (friendsObject.get("id").isJsonNull) null else friendsObject.get("id").asInt,
                        firstname = if (friendsObject.get("firstname").isJsonNull) null else friendsObject.get(
                            "firstname"
                        ).asString,
                        lastname = if (friendsObject.get("lastname").isJsonNull) null else friendsObject.get(
                            "lastname"
                        ).asString,
                        profile_picture = bitmap,
                    )
                    friendsList.add(friendsView)
                }
                val friends = FriendList (
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