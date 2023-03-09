package kr.foorun.uni_eat.feature.event.bottom_sheet

import android.graphics.Typeface
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventSortBottomSheetBinding
import kr.foorun.uni_eat.base.view.base.bottom_sheet.BaseBottomSheetFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.EventViewModel

class EventSortBottomSheetFragment(
    private val backAction : () -> Unit,
) : BaseBottomSheetFragment<FragmentEventSortBottomSheetBinding>
    (R.layout.fragment_event_sort_bottom_sheet) {

    val eventSortViewmodel: EventSortViewModel by viewModels()
    val eventViewModel : EventViewModel by viewModels({requireActivity()})

    override fun observeAndInitViewModel() {
        collapseBinding.viewModel=eventSortViewmodel.apply{
            sortMethod.observe(this@EventSortBottomSheetFragment){ pick->
                when(pick){
                    0->{
                        collapseBinding.sortNewestTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                            setTypeface(null, Typeface.BOLD)
                        }
                        collapseBinding.sortDeadlineTV.apply{
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.small_text))
                            setTypeface(null, Typeface.NORMAL)
                        }
                    }
                    1->{
                        collapseBinding.sortNewestTV.apply{
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
            is EventSortViewModel.SortEvent.SortNewst -> {
                dismiss(requireActivity().supportFragmentManager)
                eventViewModel.sortByNewst()
            }
            is EventSortViewModel.SortEvent.SortDeadline -> {
                dismiss(requireActivity().supportFragmentManager)
                eventViewModel.sortByDeadline()
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