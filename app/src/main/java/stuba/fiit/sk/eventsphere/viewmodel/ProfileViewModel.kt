package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.apiCalls

data class FriendList (
    var friends: MutableList<User>?
)

class ProfileViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    init {
        viewModelScope.launch {
            getFriends()
        }
    }

    private suspend fun getFriends() {
        val friends = apiCalls.getAllFriends(mainViewModel.loggedUser.value!!)
        mainViewModel.friendsData.value?.friends?.clear()
        if (!friends.isNullOrEmpty()) {
            mainViewModel.friendsData.value?.friends = friends

        }
    }
}
class ProfileViewModelFactory(private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}