package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel() : ViewModel() {
    private val _user = MutableLiveData<String>()
    val user: LiveData<String> = _user
    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    init {
        _user.value = "Username or email"
        _password.value = "Password"
    }

    fun updateUser(input: String) {
        _user.value = input
        _user.postValue(_user.value)
    }
    fun updatePassword(input: String) {
        _password.value = input
        _user.postValue(_user.value)
    }
}