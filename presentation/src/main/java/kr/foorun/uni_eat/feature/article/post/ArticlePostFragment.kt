package kr.foorun.uni_eat.feature.article.post

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.databinding.FragmentArticlePostBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator.Companion.GRID
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ArticlePostFragment: BaseFragment<FragmentArticlePostBinding, ArticlePostViewModel>(FragmentArticlePostBinding::inflate){

    override val fragmentViewModel: ArticlePostViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            searchTags.observe(this@ArticlePostFragment){
                searchTagAdapter.submitList(it)
                searchTagAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        tagRecycler.run{
            adapter = searchTagAdapter
            addItemDecoration(TagDecorator(spanCount = 4, oriental = GRID))
        }
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) = when (event) {
        is SearchTagViewModel.TagEvent.TagClick -> {
            val tag = event.searchTag
            val arr = ArrayList<SearchTag>()
            fragmentViewModel.searchTags.value?.map { arr.add(it) }
            for( i in arr.indices ) if(arr[i].tagName == tag.tagName) arr[i] = SearchTag(tag.tagName,!tag.isPicked)
            fragmentViewModel.setTags(arr)
        }
    }

}