package kr.foorun.uni_eat.feature.article.entire

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ArticleEntireViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EntireEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    sealed class EntireEvent{
        data class SearchClick(val unit: Unit? = null): EntireEvent()
        data class PostClick(val unit: Unit? = null): EntireEvent()
    }

    fun event(event: EntireEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun searchClick() = event(EntireEvent.SearchClick())
    fun postClick() = event(EntireEvent.PostClick())
}