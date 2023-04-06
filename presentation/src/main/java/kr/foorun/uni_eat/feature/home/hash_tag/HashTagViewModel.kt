package kr.foorun.uni_eat.feature.home.hash_tag

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
import kr.foorun.uni_eat.feature.home.HomeViewModel
import kr.foorun.uni_eat.feature.map.MapViewModel
import javax.inject.Inject

@HiltViewModel
class HashTagViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<MapViewModel.MapEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _searchTags = MutableStateFlow(listOf(SearchTag("")))
    val searchTags = _searchTags.asLiveData()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    init {
        loadTags()
        loadArticles()
    }

    private fun loadTags() = viewModelScope.launch { _searchTags.emit(listOf( //fixme test
        SearchTag("#한식",true),
        SearchTag("#중식"),
        SearchTag("일식")
    )) }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        for (i in 0 until HomeViewModel.ARTICLE_LIMIT) {
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _articles.emit(articleList)
    }
}