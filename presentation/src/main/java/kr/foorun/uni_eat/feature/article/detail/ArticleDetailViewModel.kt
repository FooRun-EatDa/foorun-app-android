package kr.foorun.uni_eat.feature.article.detail

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ArticleDetailViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ArticleDetailEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _image = MutableStateFlow<String?>(null)
    val image = _image.asLiveData()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        _image.emit("https://picsum.photos/200")
    }

    private fun event(event: ArticleDetailEvent) = viewModelScope.launch{_eventFlow.emit(event)}

    sealed class ArticleDetailEvent
}