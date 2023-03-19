package kr.foorun.di

import android.app.Application
import androidx.databinding.library.baseAdapters.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import kr.foorun.uni_eat.R

@HiltAndroidApp
class CustomApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, resources.getString(R.string.kakao_key))
    }
}