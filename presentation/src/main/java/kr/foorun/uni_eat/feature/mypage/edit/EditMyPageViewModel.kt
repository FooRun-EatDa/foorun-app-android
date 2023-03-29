package kr.foorun.uni_eat.feature.mypage.edit

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.tag.SearchTag
import kr.foorun.model.tag.getSelectedTags
import kr.foorun.model.user.User
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EditMyPageViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EditEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asLiveData()

    private val _image = MutableStateFlow<String>("")
    val image = _image.asLiveData()

    private val _tags = MutableStateFlow(listOf(SearchTag("")))
    val tags = _tags.asLiveData()

    private val _nickText = MutableStateFlow<String>("")
    val nickText = _nickText.asLiveData()

    private val _nickStringCheck = MutableStateFlow<WrongCase>(WrongCase.OutOfSize())
    val nickStringCheck = _nickStringCheck.asLiveData()

    private val _nickDuplicateCheck = MutableStateFlow(false)
    val nickDuplicateCheck = _nickDuplicateCheck.asLiveData()

    private val _introduceText = MutableStateFlow<String>("")
    val introduceText = _introduceText.asLiveData()

    val nickTextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                _nickStringCheck.value = isNickAvailable(it)
                _nickText.value = it
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    val introduceTextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                _introduceText.value = it
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    init {
        getUser()
        loadTags()
    }

    private fun getUser() = viewModelScope.launch {
        User().run {
            _user.emit(this)
            setImage(image)
        }
    }

    fun duplicateCheck(test: Boolean) = viewModelScope.launch{ //todo
        if(test) {
            _nickStringCheck.emit(WrongCase.Duplicated())
            _nickDuplicateCheck.emit(false)
        } else _nickDuplicateCheck.emit(true)
    }

    fun setImage(image: String) = viewModelScope.launch { _image.emit(image) }

    fun postUser(onSuccess: () -> Unit, onFailure: () -> Unit) = viewModelScope.launch {
        user.value?.let {
            if( nickStringCheck.value == WrongCase.Success() ||
                nickStringCheck.value == WrongCase.Nothing() ){
                val postUser = it.copy()
                postUser.name = _nickText.value
                postUser.image = _image.value
                postUser.introduce = _introduceText.value
                postUser.tags = _tags.value.getSelectedTags()
                Log.e("popo","user: $postUser")
                onSuccess()
            } else onFailure()
        }
    }

    private fun loadTags() = viewModelScope.launch { _tags.emit(listOf( //fixme test
        SearchTag("#한식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("일식"))) }

    private fun isNickAvailable(nick: String): WrongCase {
        return if(nick.isEmpty()) WrongCase.Nothing()
        else if (nick.contains(" ")) WrongCase.WrongFormat()
        else if(nick.length > 5) WrongCase.OutOfSize()
        else WrongCase.Success()
    }

    sealed class EditEvent{
        data class DoneClicked(val unit: Unit? = null): EditEvent()
        data class ImageClicked(val unit: Unit? = null): EditEvent()
        data class DuplicateCheckClicked(val unit: Unit? = null): EditEvent()
    }

    sealed class WrongCase{
        data class OutOfSize(val unit: Unit? = null): WrongCase()
        data class Nothing(val unit: Unit? = null): WrongCase()
        data class WrongFormat(val unit: Unit? = null): WrongCase()
        data class Duplicated(val unit: Unit? = null): WrongCase()
        data class Success(val unit: Unit? = null): WrongCase()
    }

    private fun event(event: EditEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun doneClicked() = event(EditEvent.DoneClicked())
    fun imageClicked() = event(EditEvent.ImageClicked())
    fun duplicateCheckClicked() = event(EditEvent.DuplicateCheckClicked())
}