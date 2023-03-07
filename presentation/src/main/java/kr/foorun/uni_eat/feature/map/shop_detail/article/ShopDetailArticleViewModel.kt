package kr.foorun.uni_eat.feature.map.shop_detail.article

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ShopDetailArticleViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ArticleEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    init {

    }

    fun event(event: ArticleEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class ArticleEvent{
        data class ArticleClick(val unit: Unit? = null): ArticleEvent()
    }

    fun articleClick() = event(ArticleEvent.ArticleClick())
}