package kr.foorun.uni_eat.feature.map

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class MapViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MapEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event (event : MapEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class MapEvent {
        data class ShowShop(val unit: Unit? = null) : MapEvent()
        data class ShowSearch(val unit: Unit? = null) : MapEvent()
    }

    fun showShopBottom() = event(MapEvent.ShowShop())
    fun showSearchBottom() = event(MapEvent.ShowSearch())
}