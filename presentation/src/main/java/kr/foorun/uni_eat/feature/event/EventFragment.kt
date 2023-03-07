package kr.foorun.uni_eat.feature.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kr.foorun.presentation.databinding.FragmentEventBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.bottom_sheet.EventSortBottomSheetFragment
import kr.foorun.presentation.R

class EventFragment :
    BaseFragment<FragmentEventBinding, EventViewModel>(FragmentEventBinding::inflate) {
    override val fragmentViewModel: EventViewModel by viewModels({ requireActivity() })
    private var eventSortBottomSheetFragment: EventSortBottomSheetFragment? = null
    private val eventAdapter: EventAdapter by lazy { EventAdapter(fragmentViewModel) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        binding {
            //리사이클러 뷰
            eventRV.apply{
                adapter = eventAdapter
                layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                addItemDecoration(EventDecoration(requireContext()))
            }

            viewModel = fragmentViewModel.apply {

                loadEvents()

                events.observe(this@EventFragment) {
                    eventAdapter.eventList = it!!
                    eventAdapter.notifyDataSetChanged()
                }

                repeatOnStarted {
                    eventFlow.collect { handleEvent(it) }
                }
            }

            eventSRL.setOnRefreshListener {
                //ToDo Like recyclerView update etc..
                binding.eventSRL.setRefreshing(false)
            }
        }
    }

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding {
        }
    }

    private fun handleEvent(event: EventViewModel.EventEvent) = when (event) {
        is EventViewModel.EventEvent.ShowSortMethod -> showBottomSheet()
        is EventViewModel.EventEvent.SortByNewest -> {
            binding.eventFilterText.text = getString(R.string.event_sort_newest)
            //ToDo
        }
        is EventViewModel.EventEvent.SortByDeadline -> {
            binding.eventFilterText.text = getString(R.string.event_sort_deadline)
            //ToDo
        }
    }

    private fun showBottomSheet() {
        eventSortBottomSheetFragment = EventSortBottomSheetFragment({ onBackPressed() })
        eventSortBottomSheetFragment?.show(
            requireActivity().supportFragmentManager,
            R.id.event_sort_FL
        )
    }

    fun onBackPressed() {
        if (eventSortBottomSheetFragment != null && eventSortBottomSheetFragment!!.handleBackKeyEvent())
            eventSortBottomSheetFragment?.hide()
    }


}