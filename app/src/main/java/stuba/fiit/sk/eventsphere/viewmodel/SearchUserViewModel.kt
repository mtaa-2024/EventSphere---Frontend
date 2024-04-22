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

class SearchUserViewModel(id: Int) : ViewModel() {
    private val _friends = MutableLiveData<FriendList>()
    val friends: LiveData<FriendList> = _friends

    private val id = id

    fun updateSearch(input: String) {
        if (input.isNotEmpty() && input != "Search" && input.length > 1) {
            viewModelScope.launch {
                getFriendsSearch(input)
            }
        }
    }

    private fun clearFriends() {
        _friends.value = FriendList(emptyList())
    }

    private suspend fun getFriendsSearch(filter: String?) {
        try {
            val fetchedJson = apiService.getFriendsSearch(filter)
            val friendsList = mutableListOf<Friend>()

            clearFriends()

            if (fetchedJson.get("result").asBoolean) {

                val friendsArray = fetchedJson.getAsJsonArray("friends").asJsonArray
                friendsArray.forEach { friendsElement ->
                    val friendsObject = friendsElement.asJsonObject

                    var bitmap: ImageBitmap? = null

                    val imageArray =
                        if (friendsObject.get("profile_image").isJsonNull) null else friendsObject.getAsJsonObject(
                            "profile_image"
                        ).getAsJsonArray("data")
                    if (imageArray != null) {
                        val image = jsonArrayToByteArray(imageArray).decodeToString()
                        val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
                        val imageBitMap = BitmapFactory.decodeByteArray(
                            decodedByteArray,
                            0,
                            decodedByteArray.size
                        )
                        bitmap = imageBitMap.asImageBitmap()
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
                    if (friendsView.id != id) {
                        friendsList.add(friendsView)
                    }
                }
                val friends = FriendList(
                    listFriends = friendsList
                )
                _friends.value = friends
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}

class SearchUserViewModelFactory(private val id: Int) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}