package kr.foorun.uni_eat.feature.event


import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.event.EventCoupon
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class EventViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EventEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _events = MutableStateFlow<List<EventCoupon>?>(null)
    val events = _events.asLiveData()

    init {
        Log.d("tgyuu","뷰모델 시작")
        viewModelScope.launch {
            _events.emit(List(10) {
                EventCoupon(
                    "노랑통닭 1000원 할인 쿠폰",
                    "노랑통닭 수원점",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dolor a eget maecenas ultricies. Mattis eget pretium libero sed faucibus pellentesque cursus vitae.",
                    "· Lorem ipsum dolor sit amet\n· Lorem ipsum dolor sit amet",
                    "https://picsum.photos/207",
                    "12.12.12",
                    "12.12.12"
                )
            })
        }
    }

    fun event(event: EventEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun showSortMethod() { event(EventEvent.ShowSortMethod()) }

    sealed class EventEvent {
        data class ShowSortMethod(val unit: Unit? = null) : EventEvent()
    }
}