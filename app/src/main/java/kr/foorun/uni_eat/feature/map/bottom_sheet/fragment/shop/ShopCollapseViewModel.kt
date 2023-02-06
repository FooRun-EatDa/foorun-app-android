package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.foorun.data.article.Article
import kr.foorun.uni_eat.base.mvvm.BaseViewModel

class ShopCollapseViewModel : BaseViewModel(){

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

    fun arrowClicked() = viewEvent(ARROW_CLICKED)

    companion object{
        const val ARROW_CLICKED = 0
    }
}