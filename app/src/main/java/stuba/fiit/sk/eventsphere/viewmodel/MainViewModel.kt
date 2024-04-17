package stuba.fiit.sk.eventsphere.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.User

class MainViewModel() : ViewModel() {
    private val _loggedUser = MutableLiveData<User>()
    val loggedUser: LiveData<User> = _loggedUser

    private val _errorRegister = MutableLiveData<String>()
    val errorRegister: LiveData<String> = _errorRegister

    suspend fun authenticateUser(username: String, password: String): Boolean {
        if (username != "" && password != "") {
            try {
                val fetchedJson = apiService.getUser(username, password)
                if (fetchedJson.get("result").asBoolean) {
                    val userObject = fetchedJson.getAsJsonArray("user")[0].asJsonObject
                    val loggedUser = User(
                        id = userObject.get("id").asInt,
                        username = userObject.get("username").asString,
                        email = userObject.get("email").asString,
                        firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                        lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                        profile_image = if (userObject.get("profile_image").isJsonNull) { null } else { userObject.get("profile_image")?.asString },
                    )
                    _loggedUser.value = loggedUser
                    println(loggedUser)
                    return true
                }
            } catch (e: Exception) {
                println("Error: $e")
                return false
            }
        }
        return false
    }

    suspend fun registerNewUser(username: String, email: String, password: String, repeatPassword: String): Boolean {
        if (username != "Username" && password != "Password" && email != "Email" && repeatPassword != "Password") {
            if (password != repeatPassword) {
                _errorRegister.value = "Password doesnt match"
                return false
            }
            try {
                val registrationData = JsonObject()
                registrationData.addProperty("username", username)
                registrationData.addProperty("email", email)
                registrationData.addProperty("password", password)
                val fetchedJson = apiService.register(registrationData)

                if (fetchedJson.get("result").asBoolean) {
                    val userObject = fetchedJson.getAsJsonArray("user")[0].asJsonObject
                    val loggedUser = User(
                        id = userObject.get("id").asInt,
                        username = userObject.get("username").asString,
                        email = userObject.get("email").asString,
                        firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                        lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                        profile_image = if (userObject.get("profile_image").isJsonNull) { null } else { userObject.get("profile_image")?.asString },
                    )
                    _loggedUser.value = loggedUser
                    return true
                } else {
                    _errorRegister.value = fetchedJson.get("text").asString
                    return false
                }

            } catch (e: Exception) {
                println("Error: $e")
                return false
            }
        }
        _errorRegister.value = "Required data not idk inputed xd"
        return false
    }

    suspend fun updateUser(){
        try {
            val fetchedJson = apiService.getUserData(loggedUser.value?.id?: 0)
            println(fetchedJson)
            if (fetchedJson.get("result").asBoolean) {
                val userObject = fetchedJson.getAsJsonArray("user")[0].asJsonObject
                val loggedUser = User(
                    id = userObject.get("id").asInt,
                    username = userObject.get("username").asString,
                    email = userObject.get("email").asString,
                    firstname = if (userObject.get("firstname").isJsonNull) { null } else { userObject.get("firstname")?.asString },
                    lastname = if (userObject.get("lastname").isJsonNull) { null } else { userObject.get("lastname")?.asString },
                    profile_image = if (userObject.get("profile_image").isJsonNull) { null } else { userObject.get("profile_image")?.asString },
                )
                _loggedUser.value = loggedUser
                println(loggedUser)
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