package kr.foorun.uni_eat.feature.map

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.BaseActivity
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.databinding.ActivityMapBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search.SearchBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop.ShopBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.ShopDetailActivity

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>({ActivityMapBinding.inflate(it)}){
    override val activityViewModel: MapViewModel by viewModels()
    private var shopBottomSheetFragment : ShopBottomSheetFragment? = null
    private var searchBottomSheetFragment : SearchBottomSheetFragment? = null

    override fun afterBinding() {
        binding {
        }
    }

    override fun observeAndInitViewModel() {
        binding {
            viewModel = activityViewModel.apply {
                repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
            }
        }
    }

    private fun showShopBottomSheet() {
        shopBottomSheetFragment = ShopBottomSheetFragment ( { onBackPressed() },{
            if(it == BottomSheetBehavior.STATE_EXPANDED)
                navigateToAct(ShopDetailActivity::class.java) {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                shopBottomSheetFragment?.collapse()
            }
            if(it == BottomSheetBehavior.STATE_HIDDEN) { shopBottomSheetFragment = null }
        }).show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun showSearchBottomSheet() {
        searchBottomSheetFragment = SearchBottomSheetFragment( { onBackPressed() } , {
            if(it == BottomSheetBehavior.STATE_HIDDEN) {
                searchBottomSheetFragment?.dismiss(supportFragmentManager)
                searchBottomSheetFragment = null
            }
        }).show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    override fun onBackPressed() {
        if (shopBottomSheetFragment != null && shopBottomSheetFragment!!.handleBackKeyEvent())
            shopBottomSheetFragment!!.hide()

        else if (searchBottomSheetFragment != null && searchBottomSheetFragment!!.handleBackKeyEvent())
            searchBottomSheetFragment!!.hide()

        else super.onBackPressed()
    }

    private fun handleEvent(event: MapViewModel.MapEvent) = when (event) {
        is MapViewModel.MapEvent.ShowShop -> showShopBottomSheet()
        is MapViewModel.MapEvent.ShowSearch -> showSearchBottomSheet()
    }
}
