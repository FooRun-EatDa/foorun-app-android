package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BasePersistBottomSheetFragment
import kr.foorun.uni_eat.databinding.LayoutSearchBottomCollapseBinding
import kr.foorun.uni_eat.databinding.LayoutSearchBottomExpandBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.search.SearchBottomSheetAdapter
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.search.SearchBottomSheetItemViewModel
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop.ShopCollapseViewModel

class SearchBottomSheetFragment(
    private val backAction: () -> Unit,
    private val stateListener: (state: Int) -> Unit
) : BasePersistBottomSheetFragment<LayoutSearchBottomCollapseBinding, LayoutSearchBottomExpandBinding>(
        R.layout.layout_search_bottom_collapse,
        R.layout.layout_search_bottom_expand
    ) {

    private val collapseViewModel: SearchCollapseViewModel by viewModels()
    private val expandViewModel: SearchExpandViewModel by viewModels()
    private val searchAdapterViewModel: SearchBottomSheetItemViewModel by viewModels()
    private val searchAdapter: SearchBottomSheetAdapter by lazy { SearchBottomSheetAdapter(searchAdapterViewModel) }

    override fun onStateChanged(state: Int) { stateListener(state) }

    override fun observeAndInitViewModel() {

        collapseBinding.viewModel = collapseViewModel.apply {
            this.onViewEvent{
                when (it) {
                        ShopCollapseViewModel.ARROW_CLICKED -> bottomSheetBehavior.run { if (state == STATE_COLLAPSED) expand() }
                    }
            }
        }

        expandBinding.viewModel = expandViewModel.apply {
            this.onViewEvent {
                when (it) {
                    ShopCollapseViewModel.ARROW_CLICKED -> bottomSheetBehavior.run { if (state == STATE_EXPANDED) collapse() }
                }
            }
        }
    }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): SearchBottomSheetFragment =
        fragmentManager.findFragmentByTag(tag) as? SearchBottomSheetFragment
            ?: SearchBottomSheetFragment(backAction, stateListener).apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

    override fun bottomSheetBackClicked() { backAction() }
}