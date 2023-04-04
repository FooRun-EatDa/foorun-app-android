package kr.foorun.uni_eat.feature.report

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ReportDialogViewModel : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<ReportEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: ReportEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun clickConfirm(){ event(ReportEvent.clickConfirm())}
    fun clickCancel(){ event(ReportEvent.clickCancel())}

    sealed class ReportEvent {
        data class clickConfirm(val unit : Unit? = null) : ReportEvent()
        data class clickCancel(val unit : Unit? = null) : ReportEvent()
    }
}