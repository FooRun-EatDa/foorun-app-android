package kr.foorun.uni_eat.feature.article.detail

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import kotlin.math.log

class ArticleDetailViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ArticleDetailEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asLiveData()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        val a = Article()
        _article.emit(a)
    }

    private fun event(event: ArticleDetailEvent) = viewModelScope.launch{_eventFlow.emit(event)}

    sealed class ArticleDetailEvent
}