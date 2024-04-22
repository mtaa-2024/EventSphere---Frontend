package stuba.fiit.sk.eventsphere.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.common.api.Response
import com.google.gson.JsonObject
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import retrofit2.http.Body
import stuba.fiit.sk.eventsphere.model.invitedUiState
import stuba.fiit.sk.eventsphere.ui.navigation.EventSphereNavHost
import stuba.fiit.sk.eventsphere.ui.theme.EventSphereTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            setContent {
                val mode = isSystemInDarkTheme()
                var darkMode by remember { mutableStateOf(mode) }

                EventSphereTheme(
                    darkTheme = darkMode
                ) {
                    Locale.setDefault(Locale("en"))
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        EventSphereNavHost(
                            setLanguage = { SetLocale(it) },
                            setTheme = { darkMode = it },
                            onInvite = { user_id, event_id ->
                                onInvite(user_id, event_id) }
                        )
                    }
                }
            }
        }

    override fun onResume() {
        super.onResume()
        start()
    }

    override fun onPause() {
        super.onPause()
        stop()
    }

    private val client by lazy { OkHttpClient() }
    private var ws: WebSocket? = null

    private fun start() {
        val request: Request = Request.Builder().url("ws://10.0.2.2:8002?id=1").build()
        val listener = object: WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, id: String) {
                invitedUiState.addEvent(id)
            }

            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                super.onOpen(webSocket, response)
            }
        }
        ws = client.newWebSocket(request, listener)
    }


    private fun stop() {
        ws?.close(1000, "Stop")
    }

    private fun onInvite(recipientId: Int, invitationId: Int) {
        val message = "{\"id\":\"$recipientId\",\"invitationId\":$invitationId}"
        ws?.send(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        client.dispatcher().executorService().shutdown()
    }

    private fun SetLocale (
        locale: Locale
    ) {
        val config = resources.configuration
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val nwInfo = connectivityManager.activeNetworkInfo ?: return false
        return nwInfo.isConnected
    }
}
