package kr.foorun.uni_eat.feature.event.bottom_sheet

import android.graphics.Typeface
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import kr.foorun.const.Constant.Companion.EVENT_SORT_DEADLINE
import kr.foorun.const.Constant.Companion.EVENT_SORT_LATEST
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventSortBottomSheetBinding
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BaseBottomSheetFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class EventSortBottomSheetFragment(
    private val backAction : () -> Unit,
) : BaseBottomSheetFragment<FragmentEventSortBottomSheetBinding>
    (R.layout.fragment_event_sort_bottom_sheet) {

    private val eventSortViewModel: EventSortViewModel by viewModels()

    override fun observeAndInitViewModel() {
        collapseBinding.viewModel=eventSortViewModel.apply{
            sortMethod.observe(this@EventSortBottomSheetFragment){ pick->
                when(pick){
                    EVENT_SORT_LATEST->{
                        collapseBinding.sortLatestTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                            setTypeface(null, Typeface.BOLD)
                        }
                        collapseBinding.sortDeadlineTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.small_text))
                            setTypeface(null, Typeface.NORMAL)
                        }
                    }
                    EVENT_SORT_DEADLINE->{
                        collapseBinding.sortLatestTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.small_text))
                            setTypeface(null, Typeface.NORMAL)
                        }
                        collapseBinding.sortDeadlineTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                            setTypeface(null, Typeface.BOLD)
                        }
                    }
                } }
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    private fun handleEvent(event : EventSortViewModel.SortEvent){
        when(event){
            is EventSortViewModel.SortEvent.SortLatest -> {
                dismiss(requireActivity().supportFragmentManager)

            }
            is EventSortViewModel.SortEvent.SortDeadline -> {
                dismiss(requireActivity().supportFragmentManager)
            }
        }
    }

    override fun onStateChanged(state: Int) {

    }

    override fun bottomSheetBackClicked() {
        backAction()
    }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): EventSortBottomSheetFragment =
        fragmentManager.findFragmentByTag(tag) as? EventSortBottomSheetFragment
            ?: EventSortBottomSheetFragment(backAction).apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }

    fun dismiss(fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .hide(this)
            .commit()
    }
}