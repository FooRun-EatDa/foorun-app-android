package kr.foorun.uni_eat.feature.event.event_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventDetailBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import java.text.SimpleDateFormat

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding, EventDetailViewModel>(
    FragmentEventDetailBinding::inflate
) {

    override val fragmentViewModel: EventDetailViewModel by viewModels()
    private val args: EventDetailFragmentArgs by navArgs()

    @SuppressLint("SimpleDateFormat")
    override fun observeAndInitViewModel() {
        val nowTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yy.MM.dd")

        binding.apply {
            eventCoupon = args.eventCoupon
            val couponEndTime = dateFormat.parse(eventCoupon!!.endDate).time
            val timeGap = (nowTime - couponEndTime)

            if(timeGap>=0){
                eventDetailUseCouponBTN.apply{
                    text = getString(R.string.end_event)
                    isEnabled = false
                    setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    setBackgroundResource(R.drawable.radius_gray_24)
                }
            }

            viewModel = fragmentViewModel.apply {
                repeatOnStarted { viewEvent.collect { handleBaseViewEvent(it) } }
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
    }
}