package kr.foorun.uni_eat.feature.event.event_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kr.foorun.presentation.databinding.FragmentEventDetailBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.EventViewModel

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding, EventDetailViewModel>(
    FragmentEventDetailBinding::inflate
) {

    override val fragmentViewModel: EventDetailViewModel by viewModels()
    val eventViewModel : EventViewModel by viewModels({requireActivity()})

    override fun observeAndInitViewModel() {
        binding.apply{
            eventDetailTV.text = eventViewModel.clickedIndex.toString() + "번째 이벤트 입니다."

            viewModel = fragmentViewModel.apply{
                repeatOnStarted {
                    viewEvent.collect{ findNavController().popBackStack() }
                }
            }
        }


    }

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding.apply{

        }
    }

}