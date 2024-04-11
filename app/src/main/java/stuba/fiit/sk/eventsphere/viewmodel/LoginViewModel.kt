package stuba.fiit.sk.eventsphere.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService

class LoginViewModel() : ViewModel() {
    var user by mutableStateOf("Username or email")
    var password by mutableStateOf("Password")

    fun updateUser(value: String) {
        user = value
    }
    fun updatePassword(value: String) {
        password = value
    }

    suspend fun onLogin() {
        if (user != "Username or email" && password != "Password") {
            try {
                println("Connecting to api")
                val fetchedJson = apiService.getUser(user, password)
                println("Fetched data from api")
                println(fetchedJson)
            } catch (e: Exception) {
                print("Error: $e")
            }
        }
    }
}

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}