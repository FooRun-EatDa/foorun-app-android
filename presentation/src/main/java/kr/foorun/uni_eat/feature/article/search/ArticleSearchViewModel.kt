package kr.foorun.uni_eat.feature.article.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.local.PreferenceManager
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleSearchViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
): BaseViewModel() {

    private val _searchWord = MutableStateFlow("")
    val searchWord = _searchWord.asLiveData()

    val searchWordWatcher: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            if(!query.isNullOrEmpty()) viewModelScope.launch { _searchWord.emit(query) }
            return true
        }
        override fun onQueryTextChange(newText: String?): Boolean {return false}
    }
}