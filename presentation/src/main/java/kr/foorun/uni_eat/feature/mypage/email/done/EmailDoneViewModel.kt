package kr.foorun.uni_eat.feature.mypage.email.done

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class EmailDoneViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EmailDoneEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    init {
        timerStart()
    }

    private fun timerStart(){
        viewModelScope.launch {
            delay(1000)
            event(EmailDoneEvent.EmailDone())
        }
    }

    sealed class EmailDoneEvent{
        data class EmailDone(val unit: Unit? = null): EmailDoneEvent()
    }

    fun event(event: EmailDoneEvent) = viewModelScope.launch { _eventFlow.emit(event) }

}