package kr.foorun.uni_eat.feature.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.uni_eat.base.view.base.BaseActivity
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.databinding.ActivitySplashBinding
import kr.foorun.uni_eat.feature.map.MapActivity

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>({ActivitySplashBinding.inflate(it)}) {

    override val activityViewModel: SplashViewModel by viewModels()

    override fun afterBinding() {}

    override fun observeAndInitViewModel() {
        activityViewModel.run {
            timerStart()
            repeatOnStarted { eventFlow.collect{ handleEvent(it) } }
        }
    }

    private fun handleEvent(event: SplashViewModel.Event) = when (event) {
        is SplashViewModel.Event.SplashDone -> navigateToAct(MapActivity::class.java){ finish() }
    }
}