package kr.foorun.uni_eat.feature.map

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.data.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MapEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _searchTags = MutableStateFlow(listOf(SearchTag("")))
    val searchTags = _searchTags.asLiveData()

    private val _searchWord = MutableStateFlow("")
    val searchWord = _searchWord.asLiveData()

    private val _visibleMainSearch = MutableStateFlow(true)
    val visibleMainSearch = _visibleMainSearch.asLiveData()

    init {
        loadTags()
    }

    private fun loadTags() = viewModelScope.launch { _searchTags.emit(listOf( //fixme test
        SearchTag("#한식"),
        SearchTag("#중식"),
        SearchTag("일식"))) }

    fun setTags(tags: List<SearchTag>) = viewModelScope.launch { _searchTags.emit(tags) }
    fun setWord(searchWord: String) = viewModelScope.launch { _searchWord.emit(searchWord) }
    fun setVisibleMainSearch(visible: Boolean) = viewModelScope.launch { _visibleMainSearch.emit(visible) }

    fun event (event : MapEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class MapEvent {
        data class ShowShop(val unit: Unit? = null) : MapEvent()
        data class ShowSearch(val unit: Unit? = null) : MapEvent()
        data class NavigateToSearch(val unit: Unit? = null) : MapEvent()
        data class LocateMap(val unit: Unit? = null) : MapEvent()
    }

    fun showShopBottom() = event(MapEvent.ShowShop())
    fun showSearchBottom() = event(MapEvent.ShowSearch())
    fun navigateToSearch() = event(MapEvent.NavigateToSearch())
    fun clickedLocateMap() = event(MapEvent.LocateMap())
}