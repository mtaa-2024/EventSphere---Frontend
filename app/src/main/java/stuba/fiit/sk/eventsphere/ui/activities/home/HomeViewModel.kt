package stuba.fiit.sk.eventsphere.ui.activities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import stuba.fiit.sk.eventsphere.model.Category

class HomeViewModel() : ViewModel() {
    private val _categories = MutableLiveData<Category>()
    val categories: LiveData<Category> = _categories

    init {
        _categories.value = Category(
            education = false,
            music = false,
            art = false,
            food = false,
            sport = false
        )
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