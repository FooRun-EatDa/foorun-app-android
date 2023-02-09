package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class SearchCollapseViewModel: BaseViewModel(){

    private val _eventFlow = MutableEventFlow<SearchCollapseEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun arrowClicked() = event(SearchCollapseEvent.ClickArrow(Unit))

    private fun event(event: SearchCollapseEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class SearchCollapseEvent{
        data class ClickArrow(val unit: Unit) : SearchCollapseEvent()
    }
}