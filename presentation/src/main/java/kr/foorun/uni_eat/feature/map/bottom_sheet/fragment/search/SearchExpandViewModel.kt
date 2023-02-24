package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.model.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class SearchExpandViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<SearchExpandEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        for (i in 0 until 11) {
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _articles.emit(articleList)
    }

    fun arrowClicked() = event(SearchExpandEvent.ClickArrow())

    private fun event(event: SearchExpandEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class SearchExpandEvent{
        data class ClickArrow(val unit: Unit? = null) : SearchExpandEvent()
    }
}