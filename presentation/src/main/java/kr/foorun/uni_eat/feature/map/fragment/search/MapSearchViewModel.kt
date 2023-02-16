package kr.foorun.uni_eat.feature.map.fragment.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.data.const.Constant.Companion.SEARCH_WORD
import kr.foorun.data.local.PreferenceManager
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MapSearchViewModel @Inject constructor(
    private val preferenceManager: PreferenceManager
): BaseViewModel() {

    private val _recentWords = MutableStateFlow(HashSet(preferenceManager.getStringList(SEARCH_WORD)))
    val recentWords = _recentWords.asLiveData()

    private val _searchWord = MutableStateFlow("")
    val searchWord = _searchWord.asLiveData()

    private val _isNonSearch = MutableStateFlow(false)
    val isNonSearch = _isNonSearch.asLiveData()

    private val _searchResult = MutableStateFlow<List<Any>?>(null) //todo load shop infos from server
    val searchResult = _searchResult.asLiveData()

    val searchWordWatcher: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            if(!query.isNullOrEmpty()) viewModelScope.launch {
                val r = HashSet(_recentWords.value).apply { add(query) }
                preferenceManager.setData(SEARCH_WORD,r)
                _recentWords.emit(r)
                _searchWord.emit(query) }
            return true
        }
        override fun onQueryTextChange(newText: String?): Boolean {return false}
    }

    fun removeRecentWord(str: String) = viewModelScope.launch {
        val res = HashSet<String>(preferenceManager.removeStringList(SEARCH_WORD, str))
        _recentWords.emit(res)
    }

    fun setIsNonSearch(boolean: Boolean) = viewModelScope.launch { _isNonSearch.emit(boolean) }
}