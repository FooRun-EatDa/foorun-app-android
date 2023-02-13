package kr.foorun.uni_eat.feature.map

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.data.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class MapViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MapEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _searchTags = MutableStateFlow(listOf(SearchTag("")))
    val searchTags = _searchTags.asLiveData()

    fun loadTags() = viewModelScope.launch { _searchTags.emit(listOf( //test
        SearchTag("#한식"),
        SearchTag("#중식"),
        SearchTag("일식"))) }

    fun setTags(tags: List<SearchTag>) = viewModelScope.launch { _searchTags.emit(tags) }

    fun event (event : MapEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class MapEvent {
        data class ShowShop(val unit: Unit? = null) : MapEvent()
        data class ShowSearch(val unit: Unit? = null) : MapEvent()
    }

    fun showShopBottom() = event(MapEvent.ShowShop())
    fun showSearchBottom() = event(MapEvent.ShowSearch())
}