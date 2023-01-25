package kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail

import androidx.activity.viewModels
import kr.foorun.uni_eat.base.BaseActivity
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
import kr.foorun.uni_eat.databinding.ActivityShopDetailBinding

class ShopDetailActivity : BaseActivity<ActivityShopDetailBinding,BaseViewModel>({ActivityShopDetailBinding.inflate(it)}) {
    override val activityViewModel: BaseViewModel by viewModels()

    override fun observeAndInitViewModel() {}

    override fun afterBinding() {}
}