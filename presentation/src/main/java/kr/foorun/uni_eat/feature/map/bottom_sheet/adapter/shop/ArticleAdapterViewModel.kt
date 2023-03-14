package kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.shop

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ArticleAdapterViewModel @Inject constructor(
): BaseViewModel(){

    private val _eventFlow = MutableEventFlow<ArticleImageEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    sealed class ArticleImageEvent{
        data class ArticleImageClicked(val unit: Unit? = null): ArticleImageEvent()
    }

    private fun event(event: ArticleImageEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    fun articleImageClicked() = event(ArticleImageEvent.ArticleImageClicked())
}