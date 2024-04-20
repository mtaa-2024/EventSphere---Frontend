package stuba.fiit.sk.eventsphere.model

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


data class LoginInput (
    var user: String,
    var password: String
)

data class RegisterInput (
    var username: String,
    var email: String,
    var password: String,
    var verifyPassword: String
)

data class LocationData (
    var address: String?,
    var latitude: Double,
    var longitude: Double
)

data class DateInput (
    var day: Int,
    var month: Int,
    var year: Int,
    var hour: Int,
    var minutes: Int,
)

data class FriendPerformer (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: ImageBitmap?
)

data class EventInput(
    var title: String,
    var description: String,
    var user_id: Int,
    var location: LocationData,
    var estimated_end: DateInput,
    var performers: List<FriendPerformer>?
)

data class EventOutput (
    val event_id: Int,
    val title: String,
    val description: String,
    val location: LocationData,
    val estimated_end: String,
    val owner_id: Int,
    val owner_firstname: String,
    val owner_lastname: String,
    val owner_profile_image: ImageBitmap?,
    val performers: MutableList<FriendPerformer>?,
    var comments: MutableList<CommentStruct>?
)

data class AddPerformerState (
    var friend: Boolean,
    var input: Boolean
)


data class User (
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String?,
    val lastname: String?,
    val profile_image: ImageBitmap?
)


data class CommentStruct (
    val id: Int,
    val firstname: String?,
    val lastname: String?,
    val profile_image: ImageBitmap?,
    val text: String?
)


data class BannerStruct (
    val id: Int,
    val title: String?,
    val location: String?,
    val date: String?
)

data class Events (
    val events: List<BannerStruct>?
)

data class CategorySelectStates (
    var education: Boolean,
    var music: Boolean,
    var art: Boolean,
    var food: Boolean,
    var sport: Boolean
)


data class EventSelectStatesCenter (
    var upcoming: Boolean,
    var expired: Boolean
)



data class EventSelectStates (
    var upcoming: Boolean,
    var attending: Boolean,
    var invited: Boolean
)

data class Friend (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: ImageBitmap?
)

data class FriendList (
    var listFriends: List<Friend>?
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
