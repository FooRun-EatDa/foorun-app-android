package kr.foorun.uni_eat.feature.mypage.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.MainNavDirections
import kr.foorun.presentation.databinding.FragmentMyPageAccountBinding
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

@AndroidEntryPoint
class MyPageAccountFragment :BaseFragment<FragmentMyPageAccountBinding,MyPageAccountViewModel>(FragmentMyPageAccountBinding::inflate){
    override val fragmentViewModel: MyPageAccountViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
            repeatOnStarted { viewEvent.collect{ popUpBackStack() } }
        }
    }

    private val kakaoLoginClass by lazy { KakaoLoginClass(requireContext(), fragmentViewModel.kakaoLoginCallback) }

    private fun handleEvent(event: MyPageAccountViewModel.AccountEvent) = when(event){
        is MyPageAccountViewModel.AccountEvent.ChangePassClicked -> {}
        is MyPageAccountViewModel.AccountEvent.LogoutClicked -> kakaoLoginClass.logout()
        is MyPageAccountViewModel.AccountEvent.WithdrawClicked -> {}
        is MyPageAccountViewModel.AccountEvent.LogoutFailure -> {}
        is MyPageAccountViewModel.AccountEvent.LogoutSuccess ->
            navigateToFrag(MainNavDirections.actionToLoginFragment())
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding{

    }

}