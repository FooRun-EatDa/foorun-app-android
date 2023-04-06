package kr.foorun.uni_eat.feature.home

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.article.Article
import kr.foorun.model.event.EventCoupon
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<HomeEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    private val _events = MutableStateFlow<List<EventCoupon>?>(null)
    val events = _events.asLiveData()

    private val _barColor = MutableStateFlow<Boolean?>(null)
    val barColor = _barColor.asLiveData()

    init {
        loadArticles()
        loadEvents()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        for (i in 0 until ARTICLE_LIMIT) {
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _articles.emit(articleList)
    }

    private fun loadEvents() = viewModelScope.launch {
        val eventList = mutableListOf<EventCoupon>()
        for (i in 0 until ARTICLE_LIMIT) {
            eventList.add(EventCoupon(
                "프레시쿡 매콤한 맛 특집", "https://picsum.photos/200","12.12.12","12.12.12",
            "조용한 골목 분위기 맛집 조용한 골목 분위기 맛집\n" +
                    "조용한 골목 분위기 맛집조용한 골목 분위기 맛집\n" +
                    "조용한 골목 분위기 맛집"))
        }
        _events.emit(eventList)
    }

    fun setBarColor(toWhite: Boolean) = viewModelScope.launch {
        if(_barColor.value != toWhite) _barColor.emit(toWhite)
    }

    sealed class HomeEvent{
        data class HashTagClicked(val unit: Unit? = null): HomeEvent()
    }

    private fun event(event: HomeEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun hashTagClicked() = event(HomeEvent.HashTagClicked())

    companion object{
        const val ARTICLE_LIMIT = 2
    }
}