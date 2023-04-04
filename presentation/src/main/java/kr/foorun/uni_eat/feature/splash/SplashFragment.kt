package kr.foorun.uni_eat.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentSplashBinding
import kr.foorun.social_login.KakaoLoginClass
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(FragmentSplashBinding::inflate) {

    override val fragmentViewModel: SplashViewModel by viewModels()
    private val kakaoLoginClass by lazy { KakaoLoginClass(requireContext(),
        tokenCallback = fragmentViewModel.kakaoLoginCallback) }

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding {
        val animSlide = AnimationUtils.loadAnimation(requireContext(), R.anim.from_left_800)
        splashImage.startAnimation(animSlide)
    }

    override fun observeAndInitViewModel() {
        fragmentViewModel.run {
            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
        }
    }

    private fun handleEvent(event: SplashViewModel.SplashEvent) = when (event) {
        is SplashViewModel.SplashEvent.SplashDone -> {
            log("splash done")
            kakaoLoginClass.hasToken()
        }
        is SplashViewModel.SplashEvent.HasToken -> {
            log("token check")
            navigateToFrag(SplashFragmentDirections.actionToHomeNav())
        }
        is SplashViewModel.SplashEvent.NoToken -> {
            log("token err: ${event.error}")
            navigateToFrag(SplashFragmentDirections.actionToLoginFragment())
        }
    }
}