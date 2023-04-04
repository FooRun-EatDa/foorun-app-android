package kr.foorun.uni_eat.feature.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.const.Constant.Companion.SPAN_COUNT
import kr.foorun.const.Constant.Companion.EVENT_SORT_DEADLINE
import kr.foorun.const.Constant.Companion.EVENT_SORT_LATEST
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.bottom_sheet.EventSortBottomSheetFragment
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.EventDecorator
import kr.foorun.uni_eat.feature.report.ReportDialogFragment

class EventFragment :
    BaseFragment<FragmentEventBinding, EventViewModel>(FragmentEventBinding::inflate) {
    override val fragmentViewModel: EventViewModel by viewModels()
    private var eventSortBottomSheetFragment: EventSortBottomSheetFragment? = null
    private val eventAdapter: EventAdapter by lazy {
        EventAdapter(EventAdapterViewModel().apply {
            repeatOnStarted { eventFlow.collect { handleAdapterEvent(it) } }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

            events.observe(this@EventFragment) {
                eventAdapter.submitList(it)
                eventAdapter.notifyDataSetChanged()
            }

            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
        bar.bringToFront()
        setDoOnBackPressed()

        eventRV.apply {
            adapter = eventAdapter
            layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(EventDecorator())
        }

        eventSRL.setOnRefreshListener {
            eventAdapter.notifyDataSetChanged()
            eventSRL.isRefreshing = false
        }

        eventNSV.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                //SwipeRefreshLayout이 최상단에 있을 때에만 동작하게 함
                eventSRL.isEnabled = (eventNSV.scrollY == 0)

                //리사이클러뷰가 상단에 올때까지 스크롤시 이벤트 바가 서서히 보임
                val topHeight = eventCL.measuredHeight.toFloat()
                bar.alpha = (scrollY / topHeight)
        }

        test.setOnClickListener {
            ReportDialogFragment().show(requireActivity().supportFragmentManager,R.id.event_FL)
        }
    }

    private fun handleEvent(event: EventViewModel.EventEvent) = when (event) {
        is EventViewModel.EventEvent.ShowSortMethod -> showBottomSheet()
    }

    private fun handleAdapterEvent(event: EventAdapterViewModel.EventAdapterEvent) = when (event) {
        is EventAdapterViewModel.EventAdapterEvent.ShowEventDetail ->
            navigateToFrag(EventFragmentDirections.actionEventFragmentToEventDetailFragment(event.clickedEventCoupon))
    }

    private fun showBottomSheet() {
        isVisibleBottomNav(false)
        eventSortBottomSheetFragment =
            EventSortBottomSheetFragment(backAction = { onBackPressed() },
                eventSortMethodListener = { sortMethod ->
                    when (sortMethod) {
                        EVENT_SORT_LATEST -> {
                            binding.eventFilterText.text = getString(R.string.event_sort_newest)
                            //ToDo
                        }
                        EVENT_SORT_DEADLINE -> {
                            binding.eventFilterText.text = getString(R.string.event_sort_deadline)
                            //ToDo
                        }
                    }

                }, stateListener = { state ->
                    if(state == BottomSheetBehavior.STATE_HIDDEN) {
                        isVisibleBottomNav(true)
                        disMissEventBottomSheet()
                    }
                }).show(requireActivity().supportFragmentManager, R.id.event_FL)
    }

    private fun onBackPressed() {
        if (eventSortBottomSheetFragment != null && eventSortBottomSheetFragment!!.handleBackKeyEvent())
            eventSortBottomSheetFragment?.hide()
        else popUpBackStack()
    }

    private fun disMissEventBottomSheet() {
        eventSortBottomSheetFragment?.dismiss(requireActivity().supportFragmentManager)
        eventSortBottomSheetFragment = null
    }

    private fun setDoOnBackPressed() = onBackPressedListener { onBackPressed() }

}