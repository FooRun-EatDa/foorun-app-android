package kr.foorun.uni_eat.feature.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.data.event.Event
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EventViewModel : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<EventEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _events = MutableStateFlow<List<Event>?>(null)
    val events = _events.asLiveData()

    private val _clickedIndex = MutableLiveData(0)
    val clickedIndex: LiveData<Int>
        get() = _clickedIndex

    fun loadEvents() = viewModelScope.launch {
        _events.emit(List(10) { index ->
            Event(
                "이벤트명",
                "https://picsum.photos/201",
                "12.12.12",
                "12.12.12",
                index
            )
        })
    }

    fun event(event: EventEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun onItemClick(index: Int) {
        _clickedIndex.value = index
        event(EventEvent.ShowEventDetail(index))
    }

    fun showSortMethod() {
        event(EventEvent.ShowSortMethod())
    }

    fun sortByNewst() {
        event(EventEvent.SortByNewest())
    }

    fun sortByDeadline() {
        event(EventEvent.SortByDeadline())
    }

    sealed class EventEvent {
        data class ShowEventDetail(val index: Int) : EventEvent()
        data class ShowSortMethod(val unit: Unit? = null) : EventEvent()
        data class SortByNewest(val unit: Unit? = null) : EventEvent()
        data class SortByDeadline(val unit: Unit? = null) : EventEvent()
    }
}