package kr.foorun.uni_eat.feature.map.shop_detail.article

import dagger.hilt.android.lifecycle.HiltViewModel
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

    sealed class ArticleEvent{
        data class OnClick(val unit: Unit? = null): ArticleEvent()
    }
}