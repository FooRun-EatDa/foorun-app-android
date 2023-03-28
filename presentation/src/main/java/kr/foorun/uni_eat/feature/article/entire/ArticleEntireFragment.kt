package kr.foorun.uni_eat.feature.article.entire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentArticleEntireBinding
import kr.foorun.presentation.databinding.ItemTabItemBinding
import kr.foorun.uni_eat.base.view.base.base_layout.BaseTab
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.entire.adapter.ArticleDetailViewPagerAdapter

@AndroidEntryPoint
class ArticleEntireFragment : BaseFragment<FragmentArticleEntireBinding, ArticleEntireViewModel>(FragmentArticleEntireBinding::inflate) {

    override val fragmentViewModel: ArticleEntireViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } } }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        entireTab.run {
            pager.adapter = ArticleDetailViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
            setTabItem(listOf(BaseTab.CustomTabItem("추천"), BaseTab.CustomTabItem("최신")))
        }
    }

    private fun handleEvent(event: ArticleEntireViewModel.EntireEvent) = when(event) {
        is ArticleEntireViewModel.EntireEvent.SearchClick -> navigateToFrag(ArticleEntireFragmentDirections.actionArticleEntireFragmentToArticleSearchFragment())
        is ArticleEntireViewModel.EntireEvent.PostClick -> navigateToFrag(ArticleEntireFragmentDirections.actionArticleEntireFragmentToArticlePostFragment())
    }



}