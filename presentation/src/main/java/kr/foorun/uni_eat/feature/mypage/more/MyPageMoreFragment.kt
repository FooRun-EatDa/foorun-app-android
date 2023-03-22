package kr.foorun.uni_eat.feature.mypage.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.collect
import kr.foorun.presentation.databinding.FragmentMyPageMoreBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class MyPageMoreFragment :BaseFragment<FragmentMyPageMoreBinding,MyPageMoreViewModel>(FragmentMyPageMoreBinding::inflate){
    override val fragmentViewModel: MyPageMoreViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted { event.collect{ handleEvent(it) } }
        }
    }

    private fun handleEvent(event: MyPageMoreViewModel.MyPageMoreEvent) = when(event){
        is MyPageMoreViewModel.MyPageMoreEvent.ProfileClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.AccountClicked -> navigateToFrag(MyPageMoreFragmentDirections.actionMyPageMoreFragmentToMyPageAccountFragment())
        is MyPageMoreViewModel.MyPageMoreEvent.NoticeClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.LocationClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.PrivateClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.ServiceInfoClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.ServiceTermClicked -> {}
        is MyPageMoreViewModel.MyPageMoreEvent.InquireClicked -> {}
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

}