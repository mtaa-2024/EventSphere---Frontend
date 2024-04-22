package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stuba.fiit.sk.eventsphere.model.webSocket

class GroupChatViewModel() : ViewModel() {
init {
    webSocket.start()
}

}


class GroupChatViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroupChatViewModel::class.java)) {
            return GroupChatViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
