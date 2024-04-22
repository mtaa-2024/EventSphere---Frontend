package stuba.fiit.sk.eventsphere.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONException
import org.json.JSONObject


val webSocket = WebSockets()

val chatUiState = ChatUiState()

class WebSockets {
    private val client by lazy { OkHttpClient() }
    private var ws: WebSocket? = null

    fun start() {
        val request: Request = Request.Builder().url("ws://10.0.2.2:8002").build()
        val listener = object: WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                chatUiState.addMessage(text)
            }

            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                super.onOpen(webSocket, response)
            }
        }
        ws = client.newWebSocket(request, listener)
    }


    fun onMessage(input: String) {
        ws?.send(input)
    }
}

class ChatUiState {
    private val _messages: MutableLiveData<List<MessageSend>> = MutableLiveData()
    val messages: LiveData<List<MessageSend>> = _messages

    data class MessageSend (
        val id: Int,
        val message: String
    )

    fun addMessage(message: String) {
        println(message)
        try {
            val jsonObject = JSONObject(message)
            val message = jsonObject.getString("message")
            val id = jsonObject.getInt("id")

            val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
            currentMessages.add(MessageSend(id, message))
            _messages.value = currentMessages
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}