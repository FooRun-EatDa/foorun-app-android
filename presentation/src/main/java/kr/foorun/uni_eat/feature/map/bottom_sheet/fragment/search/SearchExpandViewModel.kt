package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class SearchExpandViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<SearchExpandEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun arrowClicked() = event(SearchExpandEvent.ClickArrow())

    private fun event(event: SearchExpandEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class SearchExpandEvent{
        data class ClickArrow(val unit: Unit? = null) : SearchExpandEvent()
    }
}