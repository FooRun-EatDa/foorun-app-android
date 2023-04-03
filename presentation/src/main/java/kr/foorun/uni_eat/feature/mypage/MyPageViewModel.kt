package kr.foorun.uni_eat.feature.mypage

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.user.User
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MyPageEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asLiveData()

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _user.emit(User())
    }

    sealed class MyPageEvent {
        data class SchoolCertification(val unit: Unit? = null): MyPageEvent()
        data class MyPageMore(val unit: Unit? = null): MyPageEvent()
        data class WriteArticle(val unit: Unit? = null): MyPageEvent()
        data class EmailClicked(val unit: Unit? = null): MyPageEvent()
    }

    fun event(event: MyPageEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun clickedSchoolCertification() = event(MyPageEvent.SchoolCertification())
    fun clickedMyPageMore() = event(MyPageEvent.MyPageMore())
    fun clickedWriteArticle() = event(MyPageEvent.WriteArticle())
    fun emailClicked() = event(MyPageEvent.EmailClicked())
}