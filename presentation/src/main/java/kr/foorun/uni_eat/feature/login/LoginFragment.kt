package kr.foorun.uni_eat.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.databinding.FragmentLoginBinding
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.splash.SplashFragmentDirections

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(FragmentLoginBinding::inflate) {
    override val fragmentViewModel: LoginViewModel by viewModels()
    private val kakaoLoginClass by lazy { KakaoLoginClass(requireContext(),
         loginCallback = fragmentViewModel.kakaoLoginCallback) }

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            kakaoLoginClass.hasToken()
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

    private fun handleEvent(event: LoginViewModel.LoginEvent) = when(event){
        is LoginViewModel.LoginEvent.KakaoLogin -> kakaoLoginClass.authenticate()
        is LoginViewModel.LoginEvent.KakaoSuccess -> navigateToFrag(SplashFragmentDirections.actionToHomeNav())
        is LoginViewModel.LoginEvent.KakaoFailure -> toast("${event.error?.message}")
    }

}