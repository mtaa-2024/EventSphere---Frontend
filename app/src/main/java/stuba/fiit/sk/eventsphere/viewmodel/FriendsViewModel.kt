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
import com.google.gson.JsonObject
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

                var bitmap: ImageBitmap? = null

                val imageArray = if (userObject.get("profile_image").isJsonNull) null else userObject.getAsJsonObject("profile_image").getAsJsonArray("data")
                if (imageArray != null) {
                    val image = jsonArrayToByteArray(imageArray).decodeToString()
                    val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
                    val imageBitMap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
                    bitmap= imageBitMap.asImageBitmap()
                }

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
                    profile_image = bitmap,
                )
                _friend.value = friend
            }
        } catch (e: Exception) {
                println("Error: $e")

        }
    }

    suspend fun addFriend (id:Int, userId: Int){
        try {
            val addFriendData = JsonObject()
            addFriendData.addProperty("id",id)
            addFriendData.addProperty("userId",userId)
            val fetchedJson = apiService.addFriend(addFriendData)

            if (fetchedJson.get("result").asBoolean) {
                println(fetchedJson)
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