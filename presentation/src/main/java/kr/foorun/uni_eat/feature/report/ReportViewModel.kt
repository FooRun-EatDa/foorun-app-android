package kr.foorun.uni_eat.feature.report

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ReportViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ReportEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: ReportEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class ReportEvent {

    }
}