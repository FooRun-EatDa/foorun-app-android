package kr.foorun.uni_eat.feature.article.entire

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentArticleEntireBinding
import kr.foorun.presentation.databinding.ItemTabItemBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
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

        pager.adapter = ArticleDetailViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)

        TabLayoutMediator(articleTab, pager) { tab, position ->
            DataBindingUtil.bind<ItemTabItemBinding>(LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_item , null)
            )?.apply {
                val p = getTitle(position)
                title = p.first
                number = p.second
            }?.run { tab.customView = this@run.root }
        }.attach()
    }

    private fun handleEvent(event: ArticleEntireViewModel.EntireEvent) = when(event){
        is ArticleEntireViewModel.EntireEvent.SearchClick -> navigateToFrag(ArticleEntireFragmentDirections.actionArticleEntireFragmentToArticleSearchFragment())
        is ArticleEntireViewModel.EntireEvent.PostClick -> navigateToFrag(ArticleEntireFragmentDirections.actionArticleEntireFragmentToArticlePostFragment())
    }

    private fun getTitle(position: Int) = when(position){
        0 -> Pair(getString(R.string.recommend),"")
        else -> Pair(getString(R.string.latest),"")
    }

}