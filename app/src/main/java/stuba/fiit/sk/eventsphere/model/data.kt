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