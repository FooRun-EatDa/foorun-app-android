package kr.foorun.uni_eat.feature.map.shop_detail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kr.foorun.presentation.databinding.FragmentShopDetailBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.article.ShopDetailArticleAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.menu.MenuAdapter
import kr.foorun.uni_eat.feature.map.shop_detail.viewpager.ShopImageAdapter

@AndroidEntryPoint
class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>(FragmentShopDetailBinding::inflate) {
    override val fragmentViewModel: ShopDetailViewModel by viewModels()
    private val shopImageAdapter by lazy { ShopImageAdapter() }
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

            repeatOnStarted {
                eventFlow.collect{ handleEvent(it) }
            }

            repeatOnStarted {
                viewEvent.collect{ findNavController().popBackStack() }
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        binding {
            whiteBackButton.bringToFront()
            viewpagerIndicator.createIndicator(4)

            detailRecycler.adapter = shopImageAdapter
            detailRecycler.registerOnPageChangeCallback(object : OnPageChangeCallback(){
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    viewpagerIndicator.select(position)
                }
            })

            articleRecycler.layoutManager = GridLayoutManager(requireContext(),2)
            articleRecycler.adapter = shopDetailArticleAdapter

            menuRecycler.adapter = menuAdapter

            searchTagRecycler.adapter = searchTagAdapter
        }
    }

    private fun handleEvent(event: ShopDetailViewModel.ShopDetailEvent) = when(event){
        is ShopDetailViewModel.ShopDetailEvent.ShowMenu -> fragmentViewModel.changeMenuVisible()
    }
}