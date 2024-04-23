package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import stuba.fiit.sk.eventsphere.model.WebSockets
import stuba.fiit.sk.eventsphere.model.webSocket

class GroupChatViewModel() : ViewModel() {
    private val client = OkHttpClient()
    val listener = WebSockets()
    val ws: WebSocket = client.newWebSocket(Request.Builder().url("ws://10.0.2.2:8002").build(), listener)
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
