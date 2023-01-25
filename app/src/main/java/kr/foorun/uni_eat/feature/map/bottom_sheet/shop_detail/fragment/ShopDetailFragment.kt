package kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.uni_eat.base.BaseFragment
import kr.foorun.uni_eat.databinding.FragmentShopDetailBinding

class ShopDetailFragment : BaseFragment<FragmentShopDetailBinding, ShopDetailViewModel>(FragmentShopDetailBinding::inflate) {
    override val fragmentViewModel: ShopDetailViewModel by viewModels()

    override fun observeAndInitViewModel() {}

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        TODO("Not yet implemented")
    }
}