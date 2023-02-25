package kr.foorun.uni_eat.feature.mypage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MyPageEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: MyPageEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class MyPageEvent{

    }

}