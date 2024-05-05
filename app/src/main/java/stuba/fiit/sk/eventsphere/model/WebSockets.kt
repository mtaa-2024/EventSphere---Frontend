package stuba.fiit.sk.eventsphere.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject


val webSocket = WebSockets()

class WebSockets: WebSocketListener() {

    private val _messages = MutableLiveData<List<MessageSend>>()
    val messages: LiveData<List<MessageSend>>
        get() = _messages

    override fun onMessage(webSocket: WebSocket, text: String) {
        val jsonObject = JSONObject(text)
        val message = jsonObject.getString("message")
        val username = jsonObject.getString("username")
        val messageSend = MessageSend(username, message)
        addMessage(messageSend)
    }

    fun addMessage(messageSend: MessageSend) {
        val currentMessages = _messages.value.orEmpty().toMutableList()
        currentMessages.add(messageSend)
        _messages.postValue(currentMessages)
    }

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        super.onOpen(webSocket, response)
        println("open")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        println("close")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        super.onFailure(webSocket, t, response)
        println("WebSocket failure: ${t.message}")
        t.printStackTrace()
    }
}

data class MessageSend (
    val username: String,
    val message: String
)
