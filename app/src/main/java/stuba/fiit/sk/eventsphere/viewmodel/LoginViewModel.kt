package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.LoginInput

class LoginViewModel() : ViewModel() {

    private val _login = MutableLiveData<LoginInput>()
    val login: LiveData<LoginInput> = _login

    init {
        _login.value = LoginInput(
            user = "Enter your username or email",
            password = "Enter your password"
        )
    }

    fun updateUser(input: String) {
        _login.value?.user = input
    }
    fun updatePassword(input: String) {
        _login.value?.password = input
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