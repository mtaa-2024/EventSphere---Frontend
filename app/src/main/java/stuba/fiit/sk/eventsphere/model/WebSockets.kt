package stuba.fiit.sk.eventsphere.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
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
                val jsonObject = JSONObject(text)

                val message = jsonObject.getString("message")
                val id = jsonObject.getInt("id")
                ChatUiState.MessageSend(id, message)
                chatUiState.addMessage(ChatUiState.MessageSend(id, message))
            }

            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                super.onOpen(webSocket, response)
            }
        }
        ws = client.newWebSocket(request, listener)
    }


    fun onMessage(input: ChatUiState.MessageSend) {
        val sendText = "{\"message\":\"${input.message}\", \"id\":${input.id}}"
        ws?.send(sendText)
    }
}

class ChatUiState {
    //private val _messages: List<MessageSend> =
    //val messages: LiveData<List<MessageSend>> = _messages

    data class MessageSend (
        val id: Int,
        val message: String
    )

    fun addMessage(message: ChatUiState.MessageSend) {


    }
}