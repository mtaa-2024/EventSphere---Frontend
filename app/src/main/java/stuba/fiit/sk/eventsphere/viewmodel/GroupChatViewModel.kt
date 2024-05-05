package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import stuba.fiit.sk.eventsphere.model.WebSockets

class GroupChatViewModel : ViewModel() {

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
