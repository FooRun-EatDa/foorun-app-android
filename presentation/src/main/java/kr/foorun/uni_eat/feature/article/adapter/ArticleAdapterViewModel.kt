package kr.foorun.uni_eat.feature.article.adapter

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ArticleAdapterViewModel: BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ArticleEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun event(event: ArticleEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    sealed class ArticleEvent{
        data class ArticleClick(val unit: Unit? = null): ArticleEvent()
        data class MoreClick(val unit: Unit? = null): ArticleEvent()
    }

    fun articleClick() = event(ArticleEvent.ArticleClick())
    fun moreClick() = event(ArticleEvent.MoreClick())

}