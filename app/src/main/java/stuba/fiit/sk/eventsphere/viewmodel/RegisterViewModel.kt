package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.RegisterClass

class RegisterViewModel() : ViewModel() {

    private val _registerData = MutableLiveData<RegisterClass>()
    val registerData: LiveData<RegisterClass> = _registerData

    init {
        _registerData.value = RegisterClass (
            username = "Username",
            email = "Email",
            password = "Password",
            repeatPassword = "Password"

        )

    }
    fun updateUsername(input: String) {
        _registerData.value?.username = input
    }
    fun updateEmail(input: String) {
        _registerData.value?.email = input
    }
    fun updatePassword(input: String){
        _registerData.value?.password = input
    }

    fun updateRepeatedPassword(input: String){
        _registerData.value?.repeatPassword = input
    }

}

class RegisterViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}