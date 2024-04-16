package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import stuba.fiit.sk.eventsphere.api.apiService


class EditProfileViewModel() : ViewModel() {
    private val _updateProfileData = MutableLiveData<updateProfileClass>()
    val userNewData: LiveData<updateProfileClass> = _updateProfileData

    init {
        _updateProfileData.value = updateProfileClass (
            firstname = "firstname",
            lastname = "lastname",
            oldEmail = "oldEmail",
            newEmail = "newEmail",
            oldPassword = "oldPassword",
            newPassword = "newPassword"

        )

    }
    fun updateFirstname(input: String) {
        _updateProfileData.value?.firstname = input
    }
    fun updateLastname(input: String) {
        _updateProfileData.value?.lastname = input
    }
    fun updateOldEmail(input: String){
        _updateProfileData.value?.oldEmail = input
    }

    fun updateNewEmail(input: String){
        _updateProfileData.value?.newEmail = input
    }

    fun updateOldPassword(input: String){
        _updateProfileData.value?.oldPassword = input
    }

    fun updateNewPassword(input: String){
        _updateProfileData.value?.newPassword = input
    }

    suspend fun updateProfileInfo(id: Int, firstname: String, lastname: String, oldEmail: String, newEmail: String): Boolean {
        val editUserData = JsonObject()
        editUserData.addProperty("id", id)
        if(firstname != "" && firstname != "firstname"){
            editUserData.addProperty("firstname", firstname)
        }
        if(lastname != "" && lastname != "lastname"){
            editUserData.addProperty("lastname", lastname)
        }
        if(oldEmail != "" && newEmail != "" && oldEmail != "oldEmail" && newEmail != "newEmail"){
            editUserData.addProperty("oldEmail", oldEmail)
            editUserData.addProperty("newEmail", newEmail)
        }
        try {
            val fetchedJson = apiService.editUser(editUserData)
            println(fetchedJson)
            if (fetchedJson.get("result").asBoolean) {
                return true
            }
        } catch (e: Exception) {
            println("Error: $e")
            return false
        }

        return false
    }
}

class EditProfileViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

data class updateProfileClass(
    var firstname: String?,
    var lastname: String?,
    var oldEmail: String?,
    var newEmail: String?,
    var oldPassword: String?,
    var newPassword: String?
)