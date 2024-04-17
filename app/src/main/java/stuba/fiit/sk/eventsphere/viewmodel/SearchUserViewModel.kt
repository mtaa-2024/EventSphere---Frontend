package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchUserViewModel() : ViewModel() {}

class SearchUserViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}