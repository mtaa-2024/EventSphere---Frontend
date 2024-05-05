package stuba.fiit.sk.eventsphere.viewmodel


import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.common.InputImage
import stuba.fiit.sk.eventsphere.model.NewUserData
import stuba.fiit.sk.eventsphere.model.apiCalls
import stuba.fiit.sk.eventsphere.model.detector


class EditProfileViewModel(private val mainViewModel: MainViewModel, updateProfileData: NewUserData) : ViewModel() {
    val profileImage = MutableLiveData<ImageBitmap?>()
    private val _updateProfileData = MutableLiveData<NewUserData>()
    val userNewData: LiveData<NewUserData> = _updateProfileData
    val userNewDataCopy: NewUserData
    var canBeProfilePictureAdded: Boolean = false

    init {
        _updateProfileData.value = updateProfileData
        userNewDataCopy = updateProfileData.copy()
        profileImage.value = mainViewModel.loggedUser.value?.profileImage
    }


    suspend fun updateProfileOnSave(context: Context): Pair<Boolean, String> {
        println(canBeProfilePictureAdded)
        println(profileImage.value)
        println(mainViewModel.loggedUser.value?.profileImage)
        if (profileImage.value != mainViewModel.loggedUser.value?.profileImage) {
            println("Adding image")
            if (!canBeProfilePictureAdded) {
                println(canBeProfilePictureAdded)
                return Pair(false, "Your profile image is not containing any face")
            }
        }
        if (mainViewModel.loggedUser.value != null && userNewData.value != null && userNewData.value != userNewDataCopy) {
            val user = apiCalls.editUserProfile(mainViewModel.loggedUser.value!!, userNewData.value!!, userNewDataCopy, context)
            if (user != null) {
                mainViewModel.updateUser(user)
                return Pair(true, "")
            }
        }
        return Pair(false, "Something went wrong")
    }

    fun updateFirstname(input: String) {
        _updateProfileData.value?.firstname = input
    }
    fun updateLastname(input: String) {
        _updateProfileData.value?.lastname = input
    }
    fun updateOldEmail(input: String){
        _updateProfileData.value?.email = input
    }
    fun updateNewEmail(input: String){
        _updateProfileData.value?.newEmail = input
    }
}

class EditProfileViewModelFactory(private val mainViewModel: MainViewModel, private val editProfileData: NewUserData) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(mainViewModel, editProfileData) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}