package kr.foorun.uni_eat.feature.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<LoginEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: LoginEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class LoginEvent{
        data class KakaoLogin(val unit: Unit? = null): LoginEvent()
    }

    fun clickedKakaoLogin() = event(LoginEvent.KakaoLogin())
}