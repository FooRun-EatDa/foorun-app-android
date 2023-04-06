package kr.foorun.di

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kr.foorun.uni_eat.BuildConfig

@HiltAndroidApp
class CustomApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        val kakaoKey = BuildConfig.KAKAO_API_KEY
        KakaoSdk.init(this, kakaoKey)
    }
}