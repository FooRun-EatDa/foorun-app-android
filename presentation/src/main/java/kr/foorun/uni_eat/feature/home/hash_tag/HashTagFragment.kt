package kr.foorun.uni_eat.feature.home.hash_tag

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.MainNavDirections
import kr.foorun.presentation.databinding.FragmentHashTagBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.SearchTagViewModel
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleViewModel

class HashTagFragment :BaseFragment<FragmentHashTagBinding, HashTagViewModel>(FragmentHashTagBinding::inflate) {
    override val fragmentViewModel: HashTagViewModel by viewModels()
    private val articleViewModel: ShopDetailArticleViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private val articleAdapter: ShopDetailArticleAdapter by lazy { ShopDetailArticleAdapter(
        adapterViewModel = articleViewModel.apply { repeatOnStarted { eventFlow.collect{ handleArticleEvent(it)} } } ) }
    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

            articles.observe(this@HashTagFragment){
                articleAdapter.submitList(it)
            }

            searchTags.observe(this@HashTagFragment) {
                searchTagAdapter.submitList(it)
            }

            repeatOnStarted { viewEvent.collect{handleBaseViewEvent(it)} }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        articleRecycler.adapter = articleAdapter
        articleRecycler.addItemDecoration(GridSpaceItemDecoration(sideSpace = 21))

        searchTagRecycler.adapter = searchTagAdapter
        searchTagRecycler.addItemDecoration(TagDecorator())
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) {
        when (event) {
            is SearchTagViewModel.TagEvent.TagClick -> searchTagAdapter.tagClicked(event.idx,true)
        }
    }

    private fun handleArticleEvent(event: ShopDetailArticleViewModel.ArticleEvent) = when(event) {
        is ShopDetailArticleViewModel.ArticleEvent.ArticleClick -> navigateToFrag(MainNavDirections.actionToArticleDetailFragment())
    }
}