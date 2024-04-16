package stuba.fiit.sk.eventsphere.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

data class User (
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String?,
    val lastname: String?,
    val profile_image: String?
)

data class EventStruct(
    val title: String?,
    val description: String?,
    val location: String?,
    val estimated_end: String?,
    val firstname: String?,
    val lastname: String?,
    val profile_image: String?
)

data class CommentStruct (
    val firstname: String?,
    val lastname: String?,
    val profile_image: String?,
    val text: String?
)

data class PerformerStruct(
    val id: Int?,
    val firstname: String?,
    val lastname: String?,
    val profile_image: String?
)

data class Event (
    val event: EventStruct,
    val performers: List<PerformerStruct>,
    val comments: List<CommentStruct>
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

data class Category (
    var education: Boolean,
    var music: Boolean,
    var art: Boolean,
    var food: Boolean,
    var sport: Boolean
)

data class SelectedHome (
    var selectedUpcoming: Boolean,
    var selectedAttending: Boolean,
    var selectedInvited: Boolean
)

data class EventSelectStatesCenter (
    var upcoming: Boolean,
    var expired: Boolean
)

data class EventView (
    var title: String?,
    var description: String?,
    var location: String?,
    var estimated_end: String?,
    var owner_firstname: String?,
    var owner_lastname: String?,
    var owner_picture: String?,
    var performers: List<PerformersView>?,
    var comments: List<CommentsView>?
)

data class EventCreate (
    var title: String?,
    var description: String?,
    var location: String?,
    var estimated_end: String?,
    var performers: List<PerformerStruct>?
)

data class PerformersView (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?
)

data class CommentsView (
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?,
    var text: String?
)

data class EventSelectStates (
    var upcoming: Boolean,
    var attending: Boolean,
    var invited: Boolean
)

data class FriendsView (
    var id: Int?,
    var firstname: String?,
    var lastname: String?,
    var profile_picture: String?
)

data class ListFriendsView (
    var listFriends: List<FriendsView>?
)

data class RegisterClass(
    var username: String?,
    var email: String?,
    var password: String?,
    var repeatPassword: String?
)

data class LoginClass(
    var user: String?,
    var password: String?
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