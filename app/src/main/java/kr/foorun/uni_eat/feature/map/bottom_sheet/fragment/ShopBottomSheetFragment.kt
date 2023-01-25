package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment

import android.content.Intent
import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BaseBottomSheetFragment
import kr.foorun.uni_eat.databinding.LayoutShopBottomCollapseBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.CollapseViewModel.Companion.ARROW_CLICKED
import kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.ShopDetailActivity

class ShopBottomSheetFragment(private val action : () -> Unit) :
    BaseBottomSheetFragment<LayoutShopBottomCollapseBinding>
        (R.layout.layout_shop_bottom_collapse) {

    private val collapseViewModel: CollapseViewModel by viewModels()

    override fun observeAndInitViewModel() {
        collapseBinding.viewModel = collapseViewModel.apply {
            this.onViewEvent{
                when (it) {
                    ARROW_CLICKED -> {
                        bottomSheetBehavior.run {
                            when (state) {
                                BottomSheetBehavior.STATE_EXPANDED -> collapse()
                                BottomSheetBehavior.STATE_COLLAPSED -> expand()
                                else -> collapse()
                            } } } }
            }
        }

    }

    override fun onStateChanged(state: Int) {
        when (state) {
            BottomSheetBehavior.STATE_COLLAPSED -> {}
            BottomSheetBehavior.STATE_EXPANDED -> {
                navigateToAct(ShopDetailActivity::class.java) {
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
                collapse()
            }
            else -> {}
        }
    }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): ShopBottomSheetFragment =
        fragmentManager.findFragmentByTag(tag) as? ShopBottomSheetFragment
            ?: ShopBottomSheetFragment(action).apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

    override fun bottomSheetBackClicked() { action() }
}