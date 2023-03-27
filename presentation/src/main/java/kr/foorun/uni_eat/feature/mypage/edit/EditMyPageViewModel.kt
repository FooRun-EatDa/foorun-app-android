package kr.foorun.uni_eat.feature.mypage.edit

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.user.User
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EditMyPageViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EditEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asLiveData()

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.emit(User())
    }

    sealed class EditEvent{}

    private fun event(event: EditEvent) = viewModelScope.launch { _eventFlow.emit(event) }
}