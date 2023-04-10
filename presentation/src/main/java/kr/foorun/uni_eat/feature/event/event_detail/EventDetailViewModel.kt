package kr.foorun.uni_eat.feature.event.event_detail

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EventDetailViewModel : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<EventDetailEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event : EventDetailEvent)= viewModelScope.launch{ _eventFlow.emit(event) }

    fun useEventCoupon(){event(EventDetailEvent.UseEventCoupon())}

    sealed class EventDetailEvent{
        data class UseEventCoupon(val unit: Unit? = null) : EventDetailEvent()
    }
}