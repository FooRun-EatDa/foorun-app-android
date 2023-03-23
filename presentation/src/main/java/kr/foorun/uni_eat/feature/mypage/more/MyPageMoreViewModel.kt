package kr.foorun.uni_eat.feature.mypage.more

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import kr.foorun.uni_eat.feature.login.LoginViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageMoreViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MyPageMoreEvent>()
    val event = _eventFlow.asEventFlow()

    sealed class MyPageMoreEvent{
        data class ProfileClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class AccountClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class NoticeClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class ServiceInfoClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class ServiceTermClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class PrivateClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class LocationClicked(val unit: Unit? = null): MyPageMoreEvent()
        data class InquireClicked(val unit: Unit? = null): MyPageMoreEvent()
    }

    fun event(event: MyPageMoreEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun profileClicked() = event(MyPageMoreEvent.ProfileClicked())
    fun accountClicked() = event(MyPageMoreEvent.AccountClicked())
    fun noticeClicked() = event(MyPageMoreEvent.NoticeClicked())
    fun serviceInfoClicked() = event(MyPageMoreEvent.ServiceInfoClicked())
    fun serviceTermClicked() = event(MyPageMoreEvent.ServiceTermClicked())
    fun privateClicked() = event(MyPageMoreEvent.PrivateClicked())
    fun locationClicked() = event(MyPageMoreEvent.LocationClicked())
    fun inquireClicked() = event(MyPageMoreEvent.InquireClicked())
}