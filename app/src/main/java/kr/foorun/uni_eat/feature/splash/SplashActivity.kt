package kr.foorun.uni_eat.feature.splash

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.uni_eat.feature.splash.SplashViewModel.Companion.TIMER_DONE
import kr.foorun.uni_eat.feature.main.MainActivity
import kr.foorun.uni_eat.base.BaseActivity
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

            viewEvent.observe(this@SplashActivity) {
                it.getContentIfNotHandled()?.let { event ->
                    when (event) {
                        TIMER_DONE -> navigateToAct(MapActivity::class.java){ finish() }
                    } } }
        }
    }

}