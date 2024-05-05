package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.apiCalls

class SearchUserViewModel : ViewModel() {
    private var _friendsData = MutableLiveData<FriendList>()
    var friendsData: LiveData<FriendList> = _friendsData

    suspend fun updateSearch(filter: String) {
        _friendsData.value?.friends?.clear()
        if (filter.length > 2) {
            _friendsData.value = FriendList (
                friends = apiCalls.searchUsers(filter)
            )
        }
    }
}

class SearchUserViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}