package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.LoginClass

class LoginViewModel() : ViewModel() {

    private val _loginData = MutableLiveData<LoginClass>()
    val loginData: LiveData<LoginClass> = _loginData

    init {
        _loginData.value = LoginClass(
            user = "Username or email",
            password = "Password"
        )
    }

    fun updateUser(input: String) {
        _loginData.value?.user = input
    }
    fun updatePassword(input: String) {
        _loginData.value?.password = input
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