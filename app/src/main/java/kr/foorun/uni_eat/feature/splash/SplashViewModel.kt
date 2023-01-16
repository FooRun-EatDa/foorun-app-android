package kr.foorun.uni_eat.feature.splash

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kr.foorun.data.const.Constant.Companion.DELAY_TIME
import kr.foorun.data.const.CoroutineUtil.Companion.coroutineMain
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(

) : BaseViewModel() {
    private lateinit var a : Job

    fun timerStart(){
        if(::a.isInitialized) a.cancel()

        a = coroutineMain {
            //todo load data
            delay(DELAY_TIME)
            viewEvent(TIMER_DONE)
        }
    }

    companion object{
        const val TIMER_DONE = 0
    }
}