package kr.foorun.uni_eat.feature.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.databinding.FragmentMyPageBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>(FragmentMyPageBinding::inflate) {
    override val fragmentViewModel: MyPageViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding{
    }

    private fun handleEvent(event: MyPageViewModel.MyPageEvent) = when(event) {
        is MyPageViewModel.MyPageEvent.SchoolCertification -> {}
        is MyPageViewModel.MyPageEvent.MyPageMore -> {}
        is MyPageViewModel.MyPageEvent.WriteArticle -> {}
    }

}