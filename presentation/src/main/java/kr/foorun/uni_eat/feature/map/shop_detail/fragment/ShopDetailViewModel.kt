package kr.foorun.uni_eat.feature.map.shop_detail.fragment

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
class ShopDetailViewModel @Inject constructor(): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ShopDetailEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _images = MutableStateFlow<List<String>?>(null)
    val images = _images.asLiveData()

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asLiveData()

    private val _menus = MutableStateFlow<List<Pair<String,String>>?>(null)
    val menus = _menus.asLiveData()

    private val _searchTags = MutableStateFlow<List<SearchTag>?>(null)
    val searchTags = _searchTags.asLiveData()

    private val _isShowMenu = MutableStateFlow(false)
    val isShowMenu = _isShowMenu.asLiveData()

    init {
        loadShopImages()
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        val articleList = mutableListOf<Article>()
        val menuList = mutableListOf<Pair<String,String>>()
        val searchTagList = mutableListOf<SearchTag>()
        for (i in 0 until 3) {
            searchTagList.add(SearchTag("중식",false))
            menuList.add(Pair("주먹밥","20,000원"))
            articleList.add(Article("차츰","","# 조용한 # 데이트 #술집 #asdasdasdad",List(10) { "https://picsum.photos/200" }))
        }
        _searchTags.emit(searchTagList)
        _articles.emit(articleList)
        _menus.emit(menuList)
    }

    private fun loadShopImages() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        _images.emit(List(10) { "https://picsum.photos/200" })
    }

    fun changeMenuVisible() = viewModelScope.launch {
        _isShowMenu.emit(!(isShowMenu.value ?: false))
    }

    private fun event(event: ShopDetailEvent) = viewModelScope.launch{ _eventFlow.emit(event) }

    sealed class ShopDetailEvent{
        data class ShowMenu(val unit: Unit? = null): ShopDetailEvent()
    }

    fun onClickMenu() = event(ShopDetailEvent.ShowMenu())
}