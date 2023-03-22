package kr.foorun.uni_eat.feature.event

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.model.event.Event
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EventAdapterViewModel : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<EventAdapterEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: EventAdapterEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun onItemClick(clickedEvent : Event) {
        event(EventAdapterEvent.ShowEventDetail(clickedEvent))
    }

    sealed class EventAdapterEvent {
        data class ShowEventDetail(val clickedEvent: Event) : EventAdapterEvent()
    }
}