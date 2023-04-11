package kr.foorun.uni_eat.feature.event.event_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kr.foorun.model.event.EventCoupon
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventDetailBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import java.text.SimpleDateFormat

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding, EventDetailViewModel>(FragmentEventDetailBinding::inflate) {

    override val fragmentViewModel: EventDetailViewModel by viewModels()
    private val args: EventDetailFragmentArgs by navArgs()
    private val eventCouponArg: EventCoupon by lazy { args.eventCoupon }

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleEvent(it) }
                viewEvent.collect { handleBaseViewEvent(it) }
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

        eventCoupon = eventCouponArg

        if (deadlineCheck()) {
            eventDetailUseCouponBTN.run {
                text = getString(R.string.end_event)
                isEnabled = false
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                setBackgroundResource(R.drawable.radius_gray_24)
            }
        }

        if (eventCouponArg.used) {
            eventDetailUseCouponBTN.run {
                text = getString(R.string.used_event)
                isEnabled = false
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                setBackgroundResource(R.drawable.radius_gray_24)
            }
        }
    }

    fun handleEvent(event: EventDetailViewModel.EventDetailEvent) = when (event) {
        is EventDetailViewModel.EventDetailEvent.UseEventCoupon -> {
            if (deadlineCheck()) {
                binding.eventDetailUseCouponBTN.run {
                    text = getString(R.string.end_event)
                    isEnabled = false
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    setBackgroundResource(R.drawable.radius_gray_24)
                }
            } else {
                binding.apply {
                    eventCouponArg.used = true
                    eventDetailUseCouponBTN.run {
                        text = getString(R.string.used_event)
                        isEnabled = false
                        setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        setBackgroundResource(R.drawable.radius_gray_24)
                    }
                    //ToDo
                }
            }

            log(eventCouponArg.used.toString()) // forTest
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun deadlineCheck(): Boolean {
        val nowTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yy.MM.dd")
        val couponEndTime = dateFormat.parse(eventCouponArg.endDate)!!.time

        return nowTime >= couponEndTime
    }
}