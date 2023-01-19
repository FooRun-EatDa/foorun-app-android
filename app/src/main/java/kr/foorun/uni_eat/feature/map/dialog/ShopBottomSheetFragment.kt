package kr.foorun.uni_eat.feature.map.dialog

import android.content.Intent
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BottomSheetFragment
import kr.foorun.uni_eat.databinding.LayoutShopBottomCollapseBinding
import kr.foorun.uni_eat.feature.map.dialog.shop_detail.ShopDetailActivity
import kr.foorun.uni_eat.feature.splash.SplashActivity

class ShopBottomSheetFragment :
    BottomSheetFragment<LayoutShopBottomCollapseBinding,ShopBottomSheetViewModel>(R.layout.layout_shop_bottom_collapse) {

    override val fragmentViewModel: ShopBottomSheetViewModel by viewModels()

    override fun observeAndInitViewModel() {
        collapseBinding.arrowUpIV.setOnClickListener {
            bottomSheetBehavior.run {
                when(state){
                    BottomSheetBehavior.STATE_EXPANDED -> collapse()
                    BottomSheetBehavior.STATE_COLLAPSED -> expand()
                    else -> collapse()
                }
            }
        }

        bottomSheetBehavior.apply {
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when(newState){
                        BottomSheetBehavior.STATE_COLLAPSED -> { //접힘
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {  //펼쳐짐
                            navigateToAct(ShopDetailActivity::class.java){
                                addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            }
                            collapse()
                        }
                        BottomSheetBehavior.STATE_HIDDEN -> {}    //숨겨짐
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {} //절반 펼쳐짐
                        BottomSheetBehavior.STATE_DRAGGING -> {}  //드래그하는 중
                        BottomSheetBehavior.STATE_SETTLING -> {}  //(움직이다가) 안정화되는 중
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    //슬라이드 될때 offset / hide -1.0 ~ collapsed 0.0 ~ expended 1.0
                }
            })
        }
    }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): ShopBottomSheetFragment =
        fragmentManager.findFragmentByTag(tag) as? ShopBottomSheetFragment
            ?: ShopBottomSheetFragment().apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

}