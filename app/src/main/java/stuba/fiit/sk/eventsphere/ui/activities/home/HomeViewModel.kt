package stuba.fiit.sk.eventsphere.ui.activities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stuba.fiit.sk.eventsphere.model.Category
import stuba.fiit.sk.eventsphere.model.SelectedHome

class HomeViewModel() : ViewModel() {
    private val _categories = MutableLiveData<Category>()
    val categories: LiveData<Category> = _categories

    private val _selected = MutableLiveData<SelectedHome>()
    val selected: LiveData<SelectedHome> = _selected

    init {
        _categories.value = Category(
            education = false,
            music = false,
            art = false,
            food = false,
            sport = false
        )
        _selected.value = SelectedHome(
            selectedUpcoming = true,
            selectedAttending = false,
            selectedInvited = false
        )
    }

    fun onUpcomingSelect() {
        _selected.value?.selectedUpcoming = true
        _selected.value?.selectedAttending = false
        _selected.value?.selectedInvited = false
        _selected.postValue(_selected.value)
    }
    fun onAttendingSelect() {
        _selected.value?.selectedUpcoming = false
        _selected.value?.selectedAttending = true
        _selected.value?.selectedInvited = false
        _selected.postValue(_selected.value)
    }
    fun onInvitedSelect() {
        _selected.value?.selectedUpcoming = false
        _selected.value?.selectedAttending = false
        _selected.value?.selectedInvited = true
        _selected.postValue(_selected.value)
    }

    fun onClickEducation(value: Boolean) {
        _categories.value?.education = value
        _categories.postValue(_categories.value)
    }

    fun onClickMusic(value: Boolean) {
        _categories.value?.music = value
        _categories.postValue(_categories.value)
    }

    fun onClickArt(value: Boolean) {
        _categories.value?.art = value
        _categories.postValue(_categories.value)
    }

    fun onClickFood(value: Boolean) {
        _categories.value?.food = value
        _categories.postValue(_categories.value)
    }

    fun onClickSport(value: Boolean) {
        _categories.value?.sport = value
        _categories.postValue(_categories.value)
    }

    fun getEducationState(): Boolean {
        return _categories.value?.education ?: false
    }
    fun getArtState(): Boolean {
        return _categories.value?.art ?: false
    }
    fun getMusicState(): Boolean {
        return _categories.value?.music ?: false
    }
    fun getFoodState(): Boolean {
        return _categories.value?.food ?: false
    }
    fun getSportState(): Boolean {
        return _categories.value?.sport ?: false
    }

}