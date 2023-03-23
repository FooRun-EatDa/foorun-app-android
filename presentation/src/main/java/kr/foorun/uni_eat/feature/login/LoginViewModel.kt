package kr.foorun.uni_eat.feature.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import kr.foorun.uni_eat.feature.splash.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<LoginEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _kakaoLoginCallback = object : KakaoLoginClass.KakaoLoginCallback {
        override fun onSuccess(accessToken: String, email: String?) { event(LoginEvent.KakaoSuccess(accessToken, email)) }
        override fun onFailure(error: Throwable?) { event(LoginEvent.KakaoFailure(error)) }
    }
    val kakaoLoginCallback: KakaoLoginClass.KakaoLoginCallback
        get() = _kakaoLoginCallback

    fun event(event: LoginEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class LoginEvent{
        data class KakaoLogin(val unit: Unit? = null): LoginEvent()
        data class KakaoSuccess(val accessToken: String, val email: String?): LoginEvent()
        data class KakaoFailure(val error: Throwable? = null): LoginEvent()
    }

    fun clickedKakaoLogin() = event(LoginEvent.KakaoLogin())
}