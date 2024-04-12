package stuba.fiit.sk.eventsphere.model


data class User (
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String?,
    val lastname: String?,
    val profile_image: String?
)