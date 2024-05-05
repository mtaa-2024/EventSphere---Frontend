package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.LoginData

class LoginViewModel(private val loginInitialize: LoginData) : ViewModel() {

    private val _loginData = MutableLiveData<LoginData>()
    val loginData: LiveData<LoginData> = _loginData
    var loginDataCopy: LoginData

    init {
        _loginData.value = loginInitialize
        loginDataCopy = loginInitialize.copy()
    }

    fun updateUser(input: String) {
        _loginData.value?.username = input
    }
    fun updatePassword(input: String) {
        _loginData.value?.password = input
    }
}

class LoginViewModelFactory(private val loginData: LoginData) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}