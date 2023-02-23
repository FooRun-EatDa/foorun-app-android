package kr.foorun.uni_eat.feature.map

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.model.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class SearchTagViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<TagEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun tagClicked(searchTag: SearchTag) = viewModelScope.launch { event(TagEvent.TagClick(searchTag)) }

    fun event (event : TagEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class TagEvent {
        data class TagClick(val searchTag: SearchTag) : TagEvent()
    }
}