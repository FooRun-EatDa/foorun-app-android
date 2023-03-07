package kr.foorun.uni_eat.feature.event.event_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kr.foorun.data.event.Event
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentEventBinding
import kr.foorun.presentation.databinding.FragmentEventDetailBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.event.bottom_sheet.EventSortBottomSheetFragment

class EventDetailFragment : BaseFragment<FragmentEventDetailBinding, EventDetailViewModel>(
    FragmentEventDetailBinding::inflate
) {

    override val fragmentViewModel: EventDetailViewModel by viewModels()

    override fun observeAndInitViewModel() {
        binding.apply{
            fragmentViewModel.apply{
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