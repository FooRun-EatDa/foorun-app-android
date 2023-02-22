package kr.foorun.uni_eat.feature.map.shop_detail.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.databinding.FragmentShopDetailBinding
import kr.foorun.uni_eat.feature.map.shop_detail.viewpager.ShopImageAdapter

@AndroidEntryPoint
class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>(FragmentShopDetailBinding::inflate) {
    override val fragmentViewModel: ShopDetailViewModel by viewModels()
    private val  shopImageAdapter by lazy { ShopImageAdapter() }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        binding.viewModel = fragmentViewModel.apply {
            images.observe(this@ShopDetailFragment){
                shopImageAdapter.submitList(it)
                shopImageAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        binding {
            test.bringToFront()
            test2.createIndicator(4)
            detailRC.adapter = shopImageAdapter
            detailRC.registerOnPageChangeCallback(object : OnPageChangeCallback(){
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    test2.select(position)
                }
            })
        }
    }
}