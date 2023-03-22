package kr.foorun.uni_eat.feature.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.foorun.const.Constant.Companion.SPAN_COUNT
import kr.foorun.const.Constant.Companion.EVENT_SORT_DEADLINE
import kr.foorun.const.Constant.Companion.EVENT_SORT_LATEST
import kr.foorun.presentation.databinding.FragmentEventBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.bottom_sheet.EventSortBottomSheetFragment
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.view.base.recycler.decorator.EventDecorator

class EventFragment :
    BaseFragment<FragmentEventBinding, EventViewModel>(FragmentEventBinding::inflate) {
    override val fragmentViewModel: EventViewModel by viewModels()
    private var eventSortBottomSheetFragment: EventSortBottomSheetFragment? = null
    private val eventAdapter: EventAdapter by lazy { EventAdapter( EventAdapterViewModel().apply{
        repeatOnStarted { eventFlow.collect {handleAdapterEvent(it)}}
    }) }

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
        eventRV.apply {
            adapter = eventAdapter
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(EventDecorator())
        }

        eventSRL.setOnRefreshListener {
            eventAdapter.notifyDataSetChanged()
            eventSRL.isRefreshing = false
        }
        eventNSV.viewTreeObserver.addOnScrollChangedListener {
            //스크롤 뷰가 최상단에 있을 때에만 SwipeRefreshLayout이 동작하게 함.
            eventSRL.isEnabled = eventNSV.scrollY == 0
        }
    }

    private fun handleEvent(event: EventViewModel.EventEvent) = when (event) {
        is EventViewModel.EventEvent.ShowSortMethod -> showBottomSheet()
    }

    private fun handleAdapterEvent(event: EventAdapterViewModel.EventAdapterEvent) = when (event) {
        is EventAdapterViewModel.EventAdapterEvent.ShowEventDetail -> {
            navigateToFrag(EventFragmentDirections.actionEventFragmentToEventDetailFragment(event.index))
        }
    }

    private fun showBottomSheet() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        eventSortBottomSheetFragment = EventSortBottomSheetFragment({onBackPressed()}){sortMethod ->
            when(sortMethod){
                EVENT_SORT_LATEST -> {
                    binding.eventFilterText.text = getString(R.string.event_sort_newest)
                    //ToDo
                }
                EVENT_SORT_DEADLINE -> {
                    binding.eventFilterText.text = getString(R.string.event_sort_deadline)
                    //ToDo
                }
            }
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE
        }.show(
            requireActivity().supportFragmentManager,
            R.id.event_FL
            //현재는 바텀네비뷰 상단에 바텀시트 뜸
            //아직까지 명확한 요구사항이 따로 없기 때문에 수정가능성 있음
        )
    }

    private fun onBackPressed() {
        if (eventSortBottomSheetFragment != null && eventSortBottomSheetFragment!!.handleBackKeyEvent())
            eventSortBottomSheetFragment?.hide()
    }
}