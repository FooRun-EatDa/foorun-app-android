package kr.foorun.uni_eat.feature.event.event_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kr.foorun.presentation.databinding.FragmentEventDetailBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding, EventDetailViewModel>(
    FragmentEventDetailBinding::inflate
) {

    override val fragmentViewModel: EventDetailViewModel by viewModels()
    private val args: EventDetailFragmentArgs by navArgs()

    override fun observeAndInitViewModel() {

        binding.apply {
            eventCoupon = args.eventCoupon
            viewModel = fragmentViewModel.apply {
                repeatOnStarted { viewEvent.collect { handleBaseViewEvent(it) } }
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
    }
}