package kr.foorun.uni_eat.feature.report

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ReportDialogViewModel : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<ReportEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: ReportEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun clickConfirm(){ event(ReportEvent.clickConfirm())}
    fun clickMainCancel(){ event(ReportEvent.clickMainCancel())}
    fun clickRealCancel(){ event(ReportEvent.clickRealCancel())}
    fun returnMain(){ event(ReportEvent.returnMain())}
    fun reportDone(){ viewModelScope.launch{
        delay(1000)
        event(ReportEvent.reportDone())
    }}

    sealed class ReportEvent {
        data class clickConfirm(val unit : Unit? = null) : ReportEvent()
        data class reportDone(val unit : Unit? = null) : ReportEvent()
        data class clickMainCancel(val unit : Unit? = null) : ReportEvent()
        data class clickRealCancel(val unit : Unit? = null) : ReportEvent()
        data class returnMain(val unit : Unit? = null) : ReportEvent()
    }
}