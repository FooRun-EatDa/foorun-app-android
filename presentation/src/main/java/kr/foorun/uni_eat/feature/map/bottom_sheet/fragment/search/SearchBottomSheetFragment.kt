package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.LayoutSearchBottomCollapseBinding
import kr.foorun.presentation.databinding.LayoutSearchBottomExpandBinding
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BasePersistBottomSheetFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.search.SearchBottomSheetAdapter
import kr.foorun.uni_eat.feature.map.bottom_sheet.adapter.search.SearchBottomSheetItemViewModel

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
            repeatOnStarted { collapseViewModel.eventFlow.collect{ handleCollapseEvent(it)} }
        }

        expandBinding.viewModel = expandViewModel.apply {
            repeatOnStarted { expandViewModel.eventFlow.collect{ handleExpandEvent(it)} }
        }
    }

    private fun handleExpandEvent(event: SearchExpandViewModel.SearchExpandEvent) = when (event) {
        is SearchExpandViewModel.SearchExpandEvent.ClickArrow -> bottomSheetBehavior.run { if (state == STATE_EXPANDED) collapse() }
    }

    private fun handleCollapseEvent(event: SearchCollapseViewModel.SearchCollapseEvent) = when (event) {
        is SearchCollapseViewModel.SearchCollapseEvent.ClickArrow -> bottomSheetBehavior.run { if (state == STATE_COLLAPSED) expand() }
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

    fun dismiss(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .hide(this)
            .commit()
    }

    override fun bottomSheetBackClicked() { backAction() }
}