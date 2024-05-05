package stuba.fiit.sk.eventsphere.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import stuba.fiit.sk.eventsphere.R
import stuba.fiit.sk.eventsphere.model.LoginData
import stuba.fiit.sk.eventsphere.model.RegisterData
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.WebSockets
import stuba.fiit.sk.eventsphere.model.apiCalls
import stuba.fiit.sk.eventsphere.model.handler
import stuba.fiit.sk.eventsphere.model.service
import java.io.IOException
import java.util.concurrent.TimeUnit

val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
fun isEmailValid(email: String): Boolean {
    return emailRegex.matches(email)
}


class MainViewModel : ViewModel() {
    private val _loggedUser = MutableLiveData<User>()
    val loggedUser: LiveData<User> = _loggedUser

    private val _friendsData = MutableLiveData<FriendList>()
    var friendsData: LiveData<FriendList> = _friendsData

    private var loginDataS: LoginData? = null
    private var loginDataCopyS: LoginData? = null

    private val client = OkHttpClient()
    val listener = WebSockets()
    var ws: WebSocket? = null

    init {
        _friendsData.value = FriendList (
            friends = null
        )
        service.scheduleWithFixedDelay({
            handler.run {
                viewModelScope.launch {
                    if (loginDataS != null && loginDataCopyS != null) {
                        authenticateUser(loginDataS, loginDataCopyS!!)
                        println("Authenticate")
                    }
                }
            }
        }, 0, 2, TimeUnit.MINUTES);
    }

    suspend fun registerNewUser(registration: RegisterData?, registerDataCopy: RegisterData): Pair<Boolean, Int> {
        if (registration?.username == registerDataCopy.username || registration?.username == "")
            return Pair(false, R.string.username_initialize)
        if (registration?.email == registerDataCopy.email || registration?.email == "")
            return Pair(false, R.string.email_initialize)
        if (!isEmailValid(registration?.email.toString()))
            return Pair(false, R.string.email_format)
        if (registration?.password == registerDataCopy.password)
            return Pair(false, R.string.password_initialize)
        if (registration?.verifyPassword == registerDataCopy.verifyPassword)
            return Pair(false, R.string.password_initialize)
        if (registration?.password != registration?.verifyPassword)
            return Pair(false, R.string.password_match)
        if (registration?.password?.length!! < 8)
            return Pair(false, R.string.password_length)
        if (!registration.password.any { it.isDigit() })
            return Pair(false, R.string.password_digit)
        if (!registration.password.any { it.isUpperCase() })
            return Pair(false, R.string.password_upper)
        try {
            val user = registration.let { apiCalls.createUser(it) }
            if (user != null) {
                _loggedUser.value = user.copy()
                if (ws == null)
                    ws = client.newWebSocket(Request.Builder().url("https://websockets-en52qho2eq-uc.a.run.app/").build(), listener)
                return Pair(true, -1)
            }
            return Pair(false, R.string.check_internet)
        } catch (e: IOException) {
            return Pair(false, R.string.check_internet)
        }
    }

    suspend fun authenticateUser(login: LoginData?, loginDataCopy: LoginData): Pair<Boolean, Int> {
        loginDataS = login?.copy()!!
        loginDataCopyS = loginDataCopy.copy()
        if (login.username == loginDataCopy.username || login.username == "")
            return Pair(false, R.string.username_initialize)
        if (login.password == loginDataCopy.password || login.password == "")
            return Pair(false, R.string.password_initialize)
        val user = apiCalls.loginUser(login)
        if (user != null) {
            _loggedUser.value = user.copy()
            if (ws == null)
                ws = client.newWebSocket(Request.Builder().url("https://websockets-en52qho2eq-uc.a.run.app/").build(), listener)
            return Pair(true, -1)
        }
        return Pair(false, R.string.check_internet)
    }

    fun updateUser(user: User) {
        _loggedUser.value = user
    }

}


class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}