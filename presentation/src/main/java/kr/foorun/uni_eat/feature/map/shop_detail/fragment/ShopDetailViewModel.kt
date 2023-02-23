package kr.foorun.uni_eat.feature.map.shop_detail.fragment

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ShopDetailViewModel @Inject constructor(): BaseViewModel() {

    private val _images = MutableStateFlow<List<String>?>(null)
    val images = _images.asLiveData()

    init {
        loadArticles()
    }

    private fun loadArticles() = viewModelScope.launch {
        //todo for test, need to get data from server to attach articles
        _images.emit(List(10) { "https://picsum.photos/200" })
    }
}