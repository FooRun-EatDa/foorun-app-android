package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.LayoutShopBottomCollapseBinding
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BaseBottomSheetFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.ArticleFragmentDirections
import kr.foorun.uni_eat.feature.map.MapFragmentDirections
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.shop.ShopBottomSheetAdapter
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.shop.ArticleAdapterViewModel

class ShopBottomSheetFragment(
    private val backAction : () -> Unit,
    private val stateListener: (state: Int) -> Unit
) : BaseBottomSheetFragment<LayoutShopBottomCollapseBinding>
        (R.layout.layout_shop_bottom_collapse) {

    private val shopAdapter: ShopBottomSheetAdapter by lazy { ShopBottomSheetAdapter(shopAdapterViewModel.apply {
        repeatOnStarted { eventFlow.collect{ handleAdapterEvent(it) } }
    }) }

    private fun handleAdapterEvent(event: ArticleAdapterViewModel.ArticleImageEvent) = when(event) {
        is ArticleAdapterViewModel.ArticleImageEvent.ArticleImageClicked -> navigateToFrag(MapFragmentDirections.actionMapFragmentToShopDetailFragment())
    }

    private val collapseViewModel: ShopCollapseViewModel by viewModels()
    private val shopAdapterViewModel: ArticleAdapterViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        setRecycler()
        collapseBinding.viewModel = collapseViewModel.apply {

            loadArticles()

            collapseViewModel.articles.observe(this@ShopBottomSheetFragment) {
                shopAdapter.submitList(it)
                shopAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { eventFlow.collect{ handleEvent(it)} }
        }
    }

    private fun setRecycler() = binding {
        collapseBinding.shopImagesRecycler.adapter = shopAdapter
    }

    private fun handleEvent(event: ShopCollapseViewModel.ShopCollapsedEvent) = when (event) {
        is ShopCollapseViewModel.ShopCollapsedEvent.ClickArrow -> handelBottomSheet()
    }

    private fun handelBottomSheet() = bottomSheetBehavior.run {
        when (state) {
            BottomSheetBehavior.STATE_EXPANDED -> collapse()
            BottomSheetBehavior.STATE_COLLAPSED -> expand()
            else -> collapse()
        } }

    override fun onStateChanged(state: Int) { stateListener(state) }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): ShopBottomSheetFragment =
        fragmentManager.findFragmentByTag(tag) as? ShopBottomSheetFragment
            ?: ShopBottomSheetFragment(backAction, stateListener).apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

    fun dismiss(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .hide(this)
            .commit()
    }

    override fun bottomSheetBackClicked() { backAction() }
}