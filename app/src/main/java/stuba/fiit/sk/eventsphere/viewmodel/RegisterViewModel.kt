package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.api.apiService
import stuba.fiit.sk.eventsphere.model.RegisterInput

class RegisterViewModel() : ViewModel() {

    private val _register = MutableLiveData<RegisterInput>()
    val register: LiveData<RegisterInput> = _register

    init {
        _register.value = RegisterInput (
            username = "Enter your username",
            email = "Enter your email",
            password = "Enter your password",
            verifyPassword = "Verify password"

        )
    }

    fun updateUsername(input: String) {
        _register.value?.username = input
    }
    fun updateEmail(input: String) {
        _register.value?.email = input
    }
    fun updatePassword(input: String){
        _register.value?.password = input
    }
    fun updateRepeatedPassword(input: String){
        _register.value?.verifyPassword = input
    }

    suspend fun checkUsername(input: String): Pair<Boolean, String> {
        try {
            val result = apiService.usernameExists(input).get("result").asBoolean
            if (result)
                return Pair(true, "Username is already in use")
        } catch (e: Exception) {
            println(e)
        }
        return Pair(false, "")
    }

    suspend fun checkEmail(input: String): Pair<Boolean, String> {
        if (!isValidEmail(input)) {
            return Pair(true, "Invalid email address")
        }
        try {
            val result = apiService.emailExists(input).get("result").asBoolean
            if (result)
                return Pair(true, "Email is already in use")
        } catch (e: Exception) {
            println(e)
        }
        return Pair(false, "")
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex(pattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }

}

data class Result(val result: Boolean, val message: String)

class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}