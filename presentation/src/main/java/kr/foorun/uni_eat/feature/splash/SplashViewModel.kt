package kr.foorun.uni_eat.feature.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    fun timerStart(){
        viewModelScope.launch {
            delay(2000)
            event(Event.SplashDone())
        }
    }

    fun event (event : Event) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class Event {
        data class SplashDone(val unit: Unit? = null) : Event()
    }
}