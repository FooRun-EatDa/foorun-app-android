package kr.foorun.social_login

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class KakaoLoginClass(
    private val activityContext: Context,
    private val loginCallback: KakaoLoginCallback
) {

    companion object {
        val TAG: String = KakaoLoginClass::class.java.simpleName
    }

    interface KakaoLoginCallback {
        fun onSuccess(accessToken: String, email: String?)
        fun onFailure(error: Throwable?)
    }

    private val kakaoLoginClient = UserApiClient.instance
    private var accessToken: String = ""

    fun hasToken() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    kakaoLoginClient.me(callback = kakaoGetUserInfoCallback)
//                else if (error is KakaoSdkError && error.isInvalidTokenError()) //로그인 필요(에러 혹은 토큰 만료)
//                else  //기타 에러
            }
        }
//        else //로그인 필요 (토큰 없음)
    }

    fun authenticate() {
        if(kakaoLoginClient.isKakaoTalkLoginAvailable(activityContext)) {
            kakaoLoginClient.loginWithKakaoTalk(activityContext, callback = kakaoTalkLoginCallback)
        }else {
            kakaoLoginClient.loginWithKakaoAccount(activityContext, callback = kakaoAccountLoginCallback)
        }
    }

    private val kakaoTalkLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            //카카오 로그인시 사용자가 의도적으로 취소할 경우 종료
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                Log.e(TAG, "KaKaoTalkLogin Fail : User cancel", error)
                loginCallback.onFailure(error)
            }
            else {
                //카카오톡 로그인 실패 시 카카오계정으로 로그인 시도
                kakaoLoginClient.loginWithKakaoAccount(activityContext, callback = kakaoAccountLoginCallback)
            }
        } else if (token != null) {
            kakaoLoginClient.me( callback = kakaoGetUserInfoCallback)
        }
    }

    private val kakaoAccountLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "KakaoAccountLogin Fail", error)
            loginCallback.onFailure(error)
        } else if (token != null) {
            accessToken = token.accessToken
            kakaoLoginClient.me(callback = kakaoGetUserInfoCallback)
        }
    }

    private val kakaoGetUserInfoCallback: (User?, Throwable?) -> Unit = { user, error ->
        if(error != null) {
            Log.e(TAG, "KaKaoGetUserInfo Fail", error)
            loginCallback.onFailure(error)
        }else {
            val email = user?.kakaoAccount?.email
            loginCallback.onSuccess(accessToken, email)
        }
    }
}