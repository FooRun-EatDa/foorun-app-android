package kr.foorun.uni_eat.feature.article.entire.inner

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.const.Constant.Companion.POSITION
import kr.foorun.presentation.MainNavDirections
import kr.foorun.presentation.databinding.LayoutArticleEntireInnerBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.entire.ArticleEntireFragmentDirections
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleViewModel

@AndroidEntryPoint
class ArticleEntireInnerFragment: BaseFragment<LayoutArticleEntireInnerBinding, ArticleEntireInnerViewModel>(LayoutArticleEntireInnerBinding::inflate) {
    override val fragmentViewModel: ArticleEntireInnerViewModel by viewModels()
    private val adapterViewModel: ShopDetailArticleViewModel by viewModels()
    private val shopDetailArticleAdapter by lazy { ShopDetailArticleAdapter(adapterViewModel = adapterViewModel.apply {
        repeatOnStarted { eventFlow.collect{ handleAdapterEvent(it) } }
    }) }

    companion object{
        fun newInstance(position: Int): ArticleEntireInnerFragment {
            val fragment = ArticleEntireInnerFragment()
            val args = Bundle()
            args.putInt(POSITION,position)
            fragment.arguments = args

            return fragment
        }

        const val RECOMMEND = 0
        const val LATEST = 1
    }

    private val position by lazy { requireArguments().getInt(POSITION) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            articles.observe(this@ArticleEntireInnerFragment){
                shopDetailArticleAdapter.submitList(it)
                shopDetailArticleAdapter.notifyDataSetChanged()
            }

            repeatOnStarted {
                eventFlow.collect{ handleEvent(it) }
            }
        }
    }

    private fun handleEvent(event: ArticleEntireInnerViewModel.InnerEvent) = when(event){
        else -> {}
    }

    private fun handleAdapterEvent(event: ShopDetailArticleViewModel.ArticleEvent) = when(event){
        is ShopDetailArticleViewModel.ArticleEvent.ArticleClick -> navigateToFrag(
            MainNavDirections.actionToArticleDetailFragment()
        )
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        articleRecycler.addItemDecoration(GridSpaceItemDecoration())
        articleRecycler.adapter = shopDetailArticleAdapter
    }
}