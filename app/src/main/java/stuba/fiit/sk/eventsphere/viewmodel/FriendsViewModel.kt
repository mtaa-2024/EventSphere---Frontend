package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.apiCalls
import java.util.UUID

class FriendsViewModel(val friendData: User, private val mainViewModel: MainViewModel) : ViewModel() {
    private val _canBeAdded = MutableLiveData<Boolean>()
    val canBeAdded: LiveData<Boolean> = _canBeAdded

    init {
        viewModelScope.launch {
            canFriendBeAdded()
        }
    }

    private suspend fun canFriendBeAdded() {
        _canBeAdded.value = apiCalls.isFriend(mainViewModel.loggedUser.value!!, friendData)
    }

    suspend fun addAsFriend() {
        if (canBeAdded.value == true) {
            apiCalls.addFriend(mainViewModel.loggedUser.value!!, friendData)
            canFriendBeAdded()
        }
    }
}

class FriendsViewModelFactory(private val friend: User, private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)) {
            return FriendsViewModel(friend, mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}