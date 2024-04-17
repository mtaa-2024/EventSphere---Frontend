package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.LoginClass

class SearchUserViewModel() : ViewModel() {}

class SearchUserViewModelFactory : ViewModelProvider.Factory {
    private val _searchData = MutableLiveData<String>()
    val searchData: LiveData<String> = _searchData

    init{

    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}