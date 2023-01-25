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
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.ShopBottomSheetAdapter
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.ShopBottomSheetItemViewModel
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.CollapseViewModel.Companion.ARROW_CLICKED
import kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.ShopDetailActivity

class ShopBottomSheetFragment(private val backAction : () -> Unit) :
    BaseBottomSheetFragment<LayoutShopBottomCollapseBinding>
        (R.layout.layout_shop_bottom_collapse) {

    private val shopAdapter: ShopBottomSheetAdapter by lazy { ShopBottomSheetAdapter(shopAdapterViewModel) }
    private val collapseViewModel: CollapseViewModel by viewModels()
    private val shopAdapterViewModel: ShopBottomSheetItemViewModel by viewModels()

    override fun observeAndInitViewModel() {
        collapseBinding.shopImagesRV.adapter = shopAdapter

        collapseBinding.viewModel = collapseViewModel.apply {

            loadArticles()

            articles.observe(this@ShopBottomSheetFragment){
                Log.e("popo","${it[0].shopImages[0]}")
                shopAdapter.submitList(it)
                shopAdapter.notifyDataSetChanged()
            }

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
            ?: ShopBottomSheetFragment(backAction).apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

    override fun bottomSheetBackClicked() { backAction() }
}