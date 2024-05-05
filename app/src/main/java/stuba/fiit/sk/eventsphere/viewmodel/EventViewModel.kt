package stuba.fiit.sk.eventsphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import stuba.fiit.sk.eventsphere.model.Comment
import stuba.fiit.sk.eventsphere.model.Event
import stuba.fiit.sk.eventsphere.model.User
import stuba.fiit.sk.eventsphere.model.apiCalls

data class Comments(
    var comments: MutableList<Comment>?
)

class EventViewModel(val event: Event, private val viewModel: MainViewModel) : ViewModel() {

    private val _organizator = MutableLiveData<User?>()
    val organizator: LiveData<User?> = _organizator
    val comments = MutableLiveData<Comments?>()
    val isAttending = MutableLiveData<Boolean>()

    init {
        isAttending.value = false

        val list = mutableListOf<Comment>()
        event.comments?.forEach {
            list.add(it.copy())
        }
        comments.value = Comments (
            comments = list
        )

        viewModelScope.launch {
            val eventOrganizator = apiCalls.getUserData(event.ownerId)
            _organizator.value = eventOrganizator?.copy()
            val isAttendingValue = isUserAttending()
            println(isAttendingValue)
            isAttending.value = isAttendingValue
        }
    }


    suspend fun insertCommentNew (comment: String): Boolean {
        comments.value?.comments?.forEach { comments ->
            if (comments.id == viewModel.loggedUser.value?.id)
                return false
        }
        if (!viewModel.loggedUser.isInitialized)
            return false
        if (viewModel.loggedUser.value?.id == event.ownerId)
            return false

        val insertedComment = apiCalls.insertNewComment(comment, viewModel.loggedUser.value!!, event)
        if (insertedComment != null) {
            val list = mutableListOf<Comment>()
            comments.value?.comments?.forEach {
                list.add(it)
            }
            list.add(insertedComment)

            comments.value = Comments(comments = list)
        }
        return true
    }

    suspend fun attendEvent() {
        val attendingValue = apiCalls.attendEvent(event, viewModel.loggedUser.value!!)
        isAttending.value = attendingValue
    }

    private suspend fun isUserAttending(): Boolean {
        return apiCalls.isAttending(event, viewModel.loggedUser.value!!)
    }
}

class EventViewModelFactory(private val event: Event, private val mainViewModel: MainViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(event, mainViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

