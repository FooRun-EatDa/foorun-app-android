package kr.foorun.uni_eat.feature.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): BaseViewModel() {

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        for (i in 0 until ARTICLE_LIMIT) {
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _articles.emit(articleList)
    }

    companion object{
        const val ARTICLE_LIMIT = 4
    }
}