package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.RegisterData
import stuba.fiit.sk.eventsphere.model.apiCalls

class RegisterViewModel(private val registerInitialize: RegisterData) : ViewModel() {

    private val _registerData = MutableLiveData<RegisterData>()
    val registerData: LiveData<RegisterData> = _registerData
    var registerDataCopy: RegisterData

    var canBeCreated = true

    init {
        _registerData.value = registerInitialize
        registerDataCopy = registerInitialize.copy()
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
        _registerData.value?.verifyPassword = input
    }

    suspend fun checkUsername(input: String): String {
        val (result, message) = apiCalls.checkIfUsernameExists(input)
        canBeCreated = result
        return message
    }

    suspend fun checkEmail(input: String): String {
        val (result, message) = apiCalls.checkIfEmailExists(input)
        canBeCreated = result
        return message
    }
}

class RegisterViewModelFactory(private val registrationData: RegisterData) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(registrationData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}