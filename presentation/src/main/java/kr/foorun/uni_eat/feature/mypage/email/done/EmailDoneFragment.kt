package kr.foorun.uni_eat.feature.mypage.email.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kr.foorun.presentation.databinding.FragmentEmailDoneBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class EmailDoneFragment: BaseFragment<FragmentEmailDoneBinding, EmailDoneViewModel>(FragmentEmailDoneBinding::inflate) {
    override val fragmentViewModel: EmailDoneViewModel by viewModels()
    private val args: EmailDoneFragmentArgs by navArgs()

    override fun observeAndInitViewModel() = binding {
        name = args.name

        fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect{ handleEvent(it)} }
        }
    }

    private fun handleEvent(event: EmailDoneViewModel.EmailDoneEvent) = when(event) {
        is EmailDoneViewModel.EmailDoneEvent.EmailDone -> {
            log("testsetset")
            popUpBackStack()
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

}