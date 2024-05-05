package stuba.fiit.sk.eventsphere.model

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.UUID

data class User (
    val id: UUID,
    val username: String,
    val email: String,
    var firstname: String?,
    var lastname: String?,
    var profileImage: ImageBitmap?
)

data class RegisterData (
    var username: String,
    var email: String,
    var password: String,
    var verifyPassword: String
)

data class LoginData (
    var username: String,
    var password: String
)

data class NewUserData (
    var firstname: String?,
    var lastname: String?,
    var email: String?,
    var newEmail: String?,
    var profileImage: Uri?
)

data class Event (
    val id: UUID,
    var ownerId: UUID,
    var title: String,
    var description: String,
    var location: String,
    var latitude: Double,
    var longitude: Double,
    var category: Int,
    var estimatedEnd: String,
    var performers: MutableList<User>?,
    val comments: MutableList<Comment>?
)

data class Comment (
    val id: UUID,
    val text: String,
    val firstname: String?,
    val lastname: String?,
    val profileImage: ImageBitmap?
)
data class CategorySelector (
    var education: Boolean,
    var music: Boolean,
    var food: Boolean,
    var art: Boolean,
    var sport: Boolean
)

data class LocationData (
    var address: String,
    var latitude: Double,
    var longitude: Double,
)

data class DateInput (
    var year: Int,
    var month: Int,
    var day: Int,
    var hours: Int,
    var minutes: Int
)

@Composable
fun <T> observeLiveData(liveData: LiveData<T>): T? {
    val lifecycleOwner = LocalLifecycleOwner.current

    var result by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(lifecycleOwner) {
        val observer = Observer<T> { newValue ->
            result = newValue
        }
        liveData.observe(lifecycleOwner, observer)
    }

    return result
}
