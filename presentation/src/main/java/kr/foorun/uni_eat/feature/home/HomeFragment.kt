package kr.foorun.uni_eat.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentHomeBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.feature.map.shop_detail.article.ShopDetailArticleAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(FragmentHomeBinding::inflate) {
    override val fragmentViewModel: HomeViewModel by viewModels()
    private val articleAdapter: ShopDetailArticleAdapter by lazy { ShopDetailArticleAdapter() }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            articles.observe(this@HomeFragment){
                articleAdapter.submitList(it)
                articleAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        articleRecycler.adapter = articleAdapter
        articleRecycler.addItemDecoration(GridSpaceItemDecoration(sideSpace = 21))
    }

}