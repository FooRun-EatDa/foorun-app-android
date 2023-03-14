package kr.foorun.uni_eat.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentHomeBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.view.base.measuredHeight
import kr.foorun.uni_eat.base.view.base.recycler.decorator.EventDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.EventDecorator.Companion.VERTICAL
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.feature.home.adapter.HomeEventAdapter
import kr.foorun.uni_eat.feature.home.adapter.HomeEventAdapterViewModel
import kr.foorun.uni_eat.feature.map.shop_detail.article.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.article.ShopDetailArticleViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(FragmentHomeBinding::inflate) {
    override val fragmentViewModel: HomeViewModel by viewModels()
    private val articleViewModel: ShopDetailArticleViewModel by viewModels()
    private val eventViewModel: HomeEventAdapterViewModel by viewModels()
    private val articleAdapter: ShopDetailArticleAdapter by lazy { ShopDetailArticleAdapter(
        adapterViewModel = articleViewModel
    ) }
    private val eventAdapter: HomeEventAdapter by lazy { HomeEventAdapter(eventViewModel) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            articles.observe(this@HomeFragment){
                articleAdapter.submitList(it)
                articleAdapter.notifyDataSetChanged()
            }

            events.observe(this@HomeFragment){
                eventAdapter.submitList(it)
                eventAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        articleRecycler.adapter = articleAdapter
        articleRecycler.addItemDecoration(GridSpaceItemDecoration(sideSpace = 21))

        eventRecycler.adapter = eventAdapter
        eventRecycler.addItemDecoration(EventDecorator(sideSpace = 21, bottomSpace = 29, oriental = VERTICAL))

        scroll.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(scrollY >= getTopHeight()) fragmentViewModel.setBarColor(true)
            else fragmentViewModel.setBarColor(false)
        }
    }

    private fun getTopHeight(): Int = binding.run {
        return topBanner.measuredHeight() + bar.measuredHeight()
    }
}