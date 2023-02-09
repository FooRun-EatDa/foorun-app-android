package kr.foorun.uni_eat.feature.map

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.BaseActivity
import kr.foorun.uni_eat.base.view.kakao_map.kakaoMapView
import kr.foorun.uni_eat.databinding.ActivityMapBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search.SearchBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop.ShopBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.ShopDetailActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.POIItemEventListener

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>({ActivityMapBinding.inflate(it)}){
    override val activityViewModel: MapViewModel by viewModels()
    private var shopBottomSheetFragment : ShopBottomSheetFragment? = null
    private var searchBottomSheetFragment : SearchBottomSheetFragment? = null

    override fun afterBinding() {
        binding {
            showSearchBottomSheet()
            shop.setOnClickListener { showShopBottomSheet() }
            search.setOnClickListener { showSearchBottomSheet() }
        }
    }

    override fun observeAndInitViewModel() {
        binding {
            viewModel = activityViewModel.apply {

            }
        }
    }

    private fun showShopBottomSheet() {
        binding.searchCL.isVisible = false
        shopBottomSheetFragment = ShopBottomSheetFragment ( { onBackPressed() },{
            if(it == BottomSheetBehavior.STATE_EXPANDED)
                navigateToAct(ShopDetailActivity::class.java) {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                shopBottomSheetFragment?.collapse()
            }
            if(it == BottomSheetBehavior.STATE_HIDDEN) shopBottomSheetFragment = null
        }).show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun showSearchBottomSheet() {
        binding.searchCL.isVisible = true
        searchBottomSheetFragment = SearchBottomSheetFragment( { onBackPressed() } , {
//            if(it == BottomSheetBehavior.STATE_EXPANDED) binding.searchCL.background = ColorDrawsable(ContextCompat.getColor(this,R.color.white))
//            if(it == BottomSheetBehavior.STATE_COLLAPSED) binding.searchCL.background = ContextCompat.getDrawable(this,R.drawable.gradient_bottom_28)
            if(it == BottomSheetBehavior.STATE_HIDDEN) {
                binding.searchCL.isVisible = false
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
}
