package kr.foorun.uni_eat.feature.article.post

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ArticlePostViewModel @Inject constructor(

): BaseViewModel() {

    private val _searchTags = MutableStateFlow(listOf(SearchTag("")))
    val searchTags = _searchTags.asLiveData()

    init {
        loadTags()
    }

    private fun loadTags() = viewModelScope.launch { _searchTags.emit(listOf( //fixme test
        SearchTag("#한식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("#중식"),
        SearchTag("일식"))) }

    fun setTags(tags: List<SearchTag>) = viewModelScope.launch { _searchTags.emit(tags) }

}