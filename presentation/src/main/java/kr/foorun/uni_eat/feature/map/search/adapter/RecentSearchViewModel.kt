package kr.foorun.uni_eat.feature.map.search.adapter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class RecentSearchViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<RecentSearchEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun clickedRemove(word: String) = event(RecentSearchEvent.Remove(word))
    fun clicked(word: String) = event(RecentSearchEvent.Clicked(word))

    fun event(event: RecentSearchEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class RecentSearchEvent{
        data class Remove(val word: String): RecentSearchEvent()
        data class Clicked(val word: String): RecentSearchEvent()
    }
}