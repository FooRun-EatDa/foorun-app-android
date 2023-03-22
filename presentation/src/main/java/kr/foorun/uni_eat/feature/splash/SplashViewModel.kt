package kr.foorun.uni_eat.feature.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import kr.foorun.uni_eat.feature.login.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<SplashEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _kakaoLoginCallback = object : KakaoLoginClass.KakaoTokenCallback {
        override fun hasToken(accessToken: String, email: String?) { event(SplashEvent.HasToken()) }
        override fun noToken(error: Throwable?) { event(SplashEvent.NoToken(error)) }
    }
    val kakaoLoginCallback
        get() = _kakaoLoginCallback

    fun timerStart(){
        viewModelScope.launch {
            delay(2000)
            event(SplashEvent.SplashDone())
        }
    }

    fun event (event : SplashEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class SplashEvent {
        data class SplashDone(val unit: Unit? = null): SplashEvent()
        data class HasToken(val unit: Unit? = null): SplashEvent()
        data class NoToken(val error: Throwable?): SplashEvent()
    }
}