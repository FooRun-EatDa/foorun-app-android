package kr.foorun.uni_eat.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.databinding.FragmentSplashBinding

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val fragmentViewModel: SplashViewModel by viewModels()

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {}

    override fun observeAndInitViewModel() {
        fragmentViewModel.run {
            timerStart()
            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
        }
    }

    private fun handleEvent(event: SplashViewModel.Event) = when (event) {
        is SplashViewModel.Event.SplashDone -> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }
}