package kr.foorun.uni_eat.feature.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kr.foorun.const.Constant.Companion.ARTICLE_PREVIEW_MARGIN
import kr.foorun.presentation.MainNavDirections
import kr.foorun.presentation.databinding.FragmentArticleBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.dp
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.adapter.ArticleAdapter
import kr.foorun.uni_eat.feature.article.adapter.ArticleAdapterViewModel
import kotlin.math.abs

class ArticleFragment : BaseFragment<FragmentArticleBinding, ArticleViewModel>(FragmentArticleBinding::inflate) {
    override val fragmentViewModel: ArticleViewModel by viewModels()
    private val articleAdapterViewModel: ArticleAdapterViewModel by viewModels()
    private val articleAdapter by lazy { ArticleAdapter(isPager = true, viewModel = articleAdapterViewModel.apply {
        repeatOnStarted {eventFlow.collect{ handleAdapterEvent(it) }}
    }) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            articles.observe(this@ArticleFragment){
                articleAdapter.submitList(it)
                articleAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

        articleRecycler.run {
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            adapter = articleAdapter

            setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(ARTICLE_PREVIEW_MARGIN.dp))
                addTransformer{ view: View, fl: Float ->
                    val v = 1- abs(fl)
                    view.scaleY = 0.8f + v * 0.2f
                }
            })
        }
    }

    private fun handleAdapterEvent(event: ArticleAdapterViewModel.ArticleEvent) = when(event) {
        is ArticleAdapterViewModel.ArticleEvent.ArticleClick -> navigateToFrag(MainNavDirections.actionToArticleDetailFragment())
        is ArticleAdapterViewModel.ArticleEvent.MoreClick -> navigateToFrag(ArticleFragmentDirections.actionArticleFragmentToArticleEntireFragment())
    }
}