package kr.foorun.uni_eat.feature.event.bottom_sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EventSortViewModel : ViewModel() {

    private val _eventFlow = MutableEventFlow<SortEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _sortMethod = MutableLiveData(0)
    val sortMethod : LiveData<Int>
        get() = _sortMethod

    fun event(event: SortEvent) =
        viewModelScope.launch { _eventFlow.emit(event) }

    fun clickSortMethod(i : Int){ _sortMethod.value = i }

    fun confirmSortMehtod(){
        when(sortMethod.value){
            0 -> event(SortEvent.SortNewst())
            1 -> event(SortEvent.SortDeadline())
        }
    }

    sealed class SortEvent {
        data class SortNewst(val unit: Unit? = null) : SortEvent()
        data class SortDeadline(val unit: Unit? = null) : SortEvent()
    }
}