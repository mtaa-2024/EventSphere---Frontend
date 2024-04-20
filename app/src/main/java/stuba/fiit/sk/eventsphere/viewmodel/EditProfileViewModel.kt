package stuba.fiit.sk.eventsphere.viewmodel


import android.content.Context
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.api.apiService
import java.io.IOException


class EditProfileViewModel() : ViewModel() {
    private val _updateProfileData = MutableLiveData<updateProfileClass>()
    val userNewData: LiveData<updateProfileClass> = _updateProfileData

    init {
        _updateProfileData.value = updateProfileClass (
            firstname = "Enter your firstname",
            lastname = "Enter your lastname",
            oldEmail = "Enter your current email",
            newEmail = "Enter your new email",
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
        if(firstname != "" && firstname != "Enter your firstname"){
            editUserData.addProperty("firstname", firstname)
        }
        if(lastname != "" && lastname != "Enter your lastname"){
            editUserData.addProperty("lastname", lastname)
        }
        if(oldEmail != "" && newEmail != "" && oldEmail != "Enter your old email" && newEmail != "Enter your new email"){
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

    fun uriToByteArray(context: Context, uri: Uri?, userId: Int?) {
        uri ?: return
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.buffered()?.use { it.readBytes() }
            byteArray?.let {
                val base64String = Base64.encodeToString(it, Base64.DEFAULT)
                val body = JsonObject().apply {
                    addProperty("image", base64String)
                    addProperty("id", userId)
                }
                viewModelScope.launch {
                    apiService.updateImage(body)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
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