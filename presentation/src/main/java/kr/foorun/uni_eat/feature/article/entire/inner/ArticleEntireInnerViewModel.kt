package kr.foorun.uni_eat.feature.article.entire.inner

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
class ArticleEntireInnerViewModel @Inject constructor(

): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<InnerEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    init {
        //todo need to separate two way like recommend and new
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        for (i in 0 until 5) {
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _articles.emit(articleList)
    }

    fun event(event: InnerEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    sealed class InnerEvent{
    }
}