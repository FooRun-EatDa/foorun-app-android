package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.foorun.data.article.Article
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow

class ShopCollapseViewModel : BaseViewModel(){

    private val _eventFlow = MutableEventFlow<ShopCollapsedEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _articles = MutableLiveData<List<Article>>()
    val articles : LiveData<List<Article>>
        get() = _articles

    fun loadArticles(){
        //todo for test, need to get data from server to attach articles
        _articles.value = List(10){Article(
            "shopName",
            "title",
            "tags",
            List(1){ "https://picsum.photos/200"}
        )}
    }

    fun arrowClicked() = event(ShopCollapsedEvent.ClickArrow())

    private fun event(event: ShopCollapsedEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class ShopCollapsedEvent{
        data class ClickArrow(val unit: Unit? = null) : ShopCollapsedEvent()
    }
}