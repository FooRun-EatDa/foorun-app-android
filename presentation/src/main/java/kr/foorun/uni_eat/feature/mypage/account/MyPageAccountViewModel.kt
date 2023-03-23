package kr.foorun.uni_eat.feature.mypage.account

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class MyPageAccountViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<AccountEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _kakaoLoginCallback = object : KakaoLoginClass.KakaoLoginCallback {
        override fun onSuccess(accessToken: String, email: String?) { event(AccountEvent.LogoutSuccess()) }
        override fun onFailure(error: Throwable?) { event(AccountEvent.LogoutFailure(error)) }
    }

    val kakaoLoginCallback: KakaoLoginClass.KakaoLoginCallback
        get() = _kakaoLoginCallback

    sealed class AccountEvent{
        data class ChangePassClicked(val unit: Unit? = null): AccountEvent()
        data class LogoutClicked(val unit: Unit? = null): AccountEvent()
        data class WithdrawClicked(val unit: Unit? = null): AccountEvent()
        data class LogoutSuccess(val unit: Unit? = null): AccountEvent()
        data class LogoutFailure(val error: Throwable?): AccountEvent()
    }

    private fun event(event: AccountEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun changePassClicked() = event(AccountEvent.ChangePassClicked())
    fun logoutClicked() = event(AccountEvent.LogoutClicked())
    fun withdrawClicked() = event(AccountEvent.WithdrawClicked())
}