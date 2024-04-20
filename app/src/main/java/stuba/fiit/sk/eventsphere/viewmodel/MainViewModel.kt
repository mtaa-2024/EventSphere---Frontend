package stuba.fiit.sk.eventsphere.viewmodel
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.LoginInput
import stuba.fiit.sk.eventsphere.model.RegisterInput
import stuba.fiit.sk.eventsphere.model.User

class MainViewModel() : ViewModel() {
    private val _loggedUser = MutableLiveData<User>()
    val loggedUser: LiveData<User> = _loggedUser

    suspend fun authenticateUser(input: LoginInput?): Boolean {
        if (input?.user != "" && input?.user != "Enter your username or email" && input?.password != "" && input?.password != "Enter your password") {
            try {
                val fetchedJson = apiService.getUser(input?.user, input?.password)

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

                    val loggedUser = User(
                        id = userObject.get("id").asInt,
                        username = userObject.get("username").asString,
                        email = userObject.get("email").asString,
                        firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                        lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                        profile_image = bitmap,
                    )
                    _loggedUser.value = loggedUser
                    return true
                }
            } catch (e: Exception) {
                println("Error: $e")
                return false
            }
        }
        return false
    }

    suspend fun registerNewUser(input: RegisterInput?): Boolean {
        if (input?.username != "Enter your username" && input?.email != "Enter your email" && input?.password != "Enter your password" && input?.verifyPassword != "Verify password") {
            if (input?.password != input?.verifyPassword) {

                return false
            }
            if ((input?.password?.length ?: 0) < 8) {

                return false
            }
            try {
                val registrationData = JsonObject()
                registrationData.addProperty("username", input?.username)
                registrationData.addProperty("email", input?.email)
                registrationData.addProperty("password", input?.password)

                val fetchedJson = apiService.register(registrationData)

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

                    val loggedUser = User(
                        id = userObject.get("id").asInt,
                        username = userObject.get("username").asString,
                        email = userObject.get("email").asString,
                        firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                        lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                        profile_image = bitmap,
                    )
                    _loggedUser.value = loggedUser
                    return true
                } else {

                    return false
                }

            } catch (e: Exception) {
                println("Error: $e")
                return false
            }
        }

        return false
    }

    suspend fun updateUser(){
        try {
            val fetchedJson = apiService.getUserData(loggedUser.value?.id?: 0)
            println(fetchedJson)
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
                val loggedUser = User (
                    id = userObject.get("id").asInt,
                    username = userObject.get("username").asString,
                    email = userObject.get("email").asString,
                    firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                    lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                    profile_image = bitmap,
                )
                _loggedUser.value = loggedUser
            }
        } catch (e: Exception) {
            println("Error: $e")
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

fun jsonArrayToByteArray(jsonArray: JsonArray): ByteArray {
    val byteArray = ByteArray(jsonArray.size())

    for (i in 0 until jsonArray.size()) {
        byteArray[i] = jsonArray[i].asInt.toByte()
    }

    return byteArray
}
