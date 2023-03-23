package kr.foorun.uni_eat.feature.map.shop_detail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kr.foorun.const.Constant.Companion.INDICATOR_COUNT
import kr.foorun.presentation.databinding.FragmentShopDetailBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.menu.MenuAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager.ShopImageAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager.ShopImageViewModel

@AndroidEntryPoint
class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>(FragmentShopDetailBinding::inflate) {
    override val fragmentViewModel: ShopDetailViewModel by viewModels()
    private val shopDetailViewModel: ShopImageViewModel by viewModels()
    private val shopImageAdapter by lazy { ShopImageAdapter(shopDetailViewModel.apply {
        repeatOnStarted { eventFlow.collect{} } }) }
    private val shopDetailArticleAdapter by lazy { ShopDetailArticleAdapter() }
    private val menuAdapter by lazy { MenuAdapter() }
    private val searchTagAdapter by lazy { SearchTagAdapter() }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        binding.viewModel = fragmentViewModel.apply {
            images.observe(this@ShopDetailFragment){
                shopImageAdapter.submitList(it)
                shopImageAdapter.notifyDataSetChanged()
            }

            articles.observe(this@ShopDetailFragment){
                shopDetailArticleAdapter.submitList(it)
                shopDetailArticleAdapter.notifyDataSetChanged()
                binding.articleRecycler.visibility = View.VISIBLE
            }

            menus.observe(this@ShopDetailFragment){
                menuAdapter.submitList(it)
                menuAdapter.notifyDataSetChanged()
            }

            searchTags.observe(this@ShopDetailFragment){
                searchTagAdapter.submitList(it)
                searchTagAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        binding {
            whiteBackButton.bringToFront()

            pager2.setIndicator(INDICATOR_COUNT)
            pager2.setPager(shopImageAdapter)

            articleRecycler.addItemDecoration(GridSpaceItemDecoration(spanCount = 2, gapSpace = 7))
            articleRecycler.adapter = shopDetailArticleAdapter

            menuRecycler.adapter = menuAdapter

            searchTagRecycler.adapter = searchTagAdapter
            searchTagRecycler.addItemDecoration(TagDecorator())
        }
    }

    private fun handleEvent(event: ShopDetailViewModel.ShopDetailEvent) = when(event){
        is ShopDetailViewModel.ShopDetailEvent.ShowMenu -> fragmentViewModel.changeMenuVisible()
    }
}