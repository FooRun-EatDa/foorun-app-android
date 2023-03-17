package kr.foorun.uni_eat.feature.article.post

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.model.tag.SearchTag
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

@HiltViewModel
class ArticlePostViewModel @Inject constructor(

): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<PostEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _searchTags = MutableStateFlow(listOf(SearchTag("")))
    val searchTags = _searchTags.asLiveData()

    private val _articleImage = MutableStateFlow<String?>(null)
    val articleImage = _articleImage.asLiveData()

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
    fun setArticleImage(image: String) = viewModelScope.launch { _articleImage.emit(image) }

    sealed class PostEvent{
        data class ImageClicked(val unit: Unit? = null): PostEvent()
        data class DoneClicked(val unit: Unit? = null): PostEvent()
    }

    fun event(event: PostEvent) = viewModelScope.launch { _eventFlow.emit(event) }

    fun imageClicked() = event(PostEvent.ImageClicked())
    fun doneClicked() = event(PostEvent.DoneClicked())




}