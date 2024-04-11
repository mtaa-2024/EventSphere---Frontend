package stuba.fiit.sk.eventsphere.api


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
