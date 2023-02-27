package kr.foorun.uni_eat.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kr.foorun.presentation.databinding.FragmentLoginBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding,LoginViewModel>(FragmentLoginBinding::inflate) {
    override val fragmentViewModel: LoginViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

    private fun handleEvent(event: LoginViewModel.LoginEvent) = when(event){
        is LoginViewModel.LoginEvent.KakaoLogin -> navigateToFrag(LoginFragmentDirections.actionLoginFragmentToMapFragment())
    }

}