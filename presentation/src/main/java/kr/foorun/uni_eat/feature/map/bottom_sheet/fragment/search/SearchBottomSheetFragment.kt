package kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.LayoutSearchBottomCollapseBinding
import kr.foorun.presentation.databinding.LayoutSearchBottomExpandBinding
import kr.foorun.uni_eat.base.view.base.recycler.decorator.grid.GridSpaceItemDecoration
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BasePersistBottomSheetFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.ShopDetailArticleAdapter

class SearchBottomSheetFragment(
    private val searchWord: String,
    private val backAction: () -> Unit,
    private val stateListener: (state: Int) -> Unit,
) : BasePersistBottomSheetFragment<LayoutSearchBottomCollapseBinding, LayoutSearchBottomExpandBinding>(
        R.layout.layout_search_bottom_collapse,
        R.layout.layout_search_bottom_expand,
        searchWord = searchWord
    ) {

    private val collapseViewModel: SearchCollapseViewModel by viewModels()
    private val expandViewModel: SearchExpandViewModel by viewModels()
    private val articleAdapter: ShopDetailArticleAdapter by lazy { ShopDetailArticleAdapter() }

    override fun onStateChanged(state: Int) { stateListener(state) }

    override fun observeAndInitViewModel() {

        collapseBinding.viewModel = collapseViewModel.apply {
            repeatOnStarted { collapseViewModel.eventFlow.collect{ handleCollapseEvent(it)} }
        }

        expandBinding.viewModel = expandViewModel.apply {

            articles.observe(this@SearchBottomSheetFragment){
                articleAdapter.submitList(it)
            }

            repeatOnStarted { expandViewModel.eventFlow.collect{ handleExpandEvent(it)} }
        }

        expandBinding.run {
            articleRecycler.adapter = articleAdapter
            articleRecycler.addItemDecoration(GridSpaceItemDecoration(spanCount = 2, gapSpace = 7))
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
            ?: SearchBottomSheetFragment(searchWord, backAction, stateListener).apply {
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