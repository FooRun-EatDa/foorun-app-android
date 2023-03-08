package kr.foorun.uni_eat.feature.main

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel() {

    private val _visibleBottom = MutableStateFlow(false)
    val visibleBottom = _visibleBottom.asLiveData()

    fun setBottomVisible(isVisible: Boolean) = viewModelScope.launch { _visibleBottom.emit(isVisible) }

}