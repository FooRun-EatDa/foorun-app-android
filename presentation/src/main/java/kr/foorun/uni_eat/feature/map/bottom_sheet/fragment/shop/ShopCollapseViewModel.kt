package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ShopCollapseViewModel @Inject constructor(): BaseViewModel(){

    private val _eventFlow = MutableEventFlow<ShopCollapsedEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        _articles.emit(List(10){Article(
            "shopName",
            "title",
            "tags",
            List(1){ "https://picsum.photos/200"}
        )})
    }

    fun arrowClicked() = event(ShopCollapsedEvent.ClickArrow())

    private fun event(event: ShopCollapsedEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class ShopCollapsedEvent{
        data class ClickArrow(val unit: Unit? = null) : ShopCollapsedEvent()
    }
}