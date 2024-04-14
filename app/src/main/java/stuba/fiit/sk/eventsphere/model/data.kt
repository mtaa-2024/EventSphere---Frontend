package stuba.fiit.sk.eventsphere.model

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

data class UpcomingStruct (
    val id: Int,
    val title: String?,
    val location: String?,
    val date: String?
)

data class Upcoming(
    val events: List<UpcomingStruct>?
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