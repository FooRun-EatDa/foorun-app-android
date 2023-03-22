package kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ShopImageViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<ShopImageEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    sealed class ShopImageEvent{
        data class ImageClicked(val unit: Unit? = null): ShopImageEvent()
    }

    fun event(event:ShopImageEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun imageClicked() = event(ShopImageEvent.ImageClicked())
}