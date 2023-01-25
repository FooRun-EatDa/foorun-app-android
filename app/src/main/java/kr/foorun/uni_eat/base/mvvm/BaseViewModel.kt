package kr.foorun.uni_eat.base.mvvm


import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import kr.foorun.data.const.Constant.Companion.BACK
import kr.foorun.data.const.Constant.Companion.LANGUAGE
import kr.foorun.data.local.PreferenceManager
import java.util.*
import javax.inject.Inject

//Don't use ObservableField if you wanna use that one , make sure about the lifecycle
open class BaseViewModel: ViewModel(){

    companion object {
        private val ENGLISH = Locale.US.toLanguageTag()
        private val KOREAN = Locale.KOREA.toLanguageTag()
        private val JAPANESE = Locale.JAPAN.toLanguageTag()
        const val CHINA = "zh-Hans-CN"
        const val INDONESIAN = "id-ID"
        const val INDIA = "hi-IN"
        const val PORTUGUESE = "pt-BR"
        const val VIETNAMESE = "vi-VN"
    }

    @Inject protected lateinit var preferenceManager: PreferenceManager
    @Inject protected lateinit var application: Application

    private val _viewEvent = MutableLiveData<Event<Any>>()
    val viewEvent: LiveData<Event<Any>>
        get() = _viewEvent

    fun getCurrentLocale(): Locale {
        val currentLocale = preferenceManager.getString(LANGUAGE)
        return if(currentLocale.isNotEmpty()) {
            when (currentLocale) {
                ENGLISH -> Locale.US
                KOREAN -> Locale.KOREA
                CHINA -> Locale.forLanguageTag(CHINA)
                INDONESIAN -> Locale.forLanguageTag(INDONESIAN)
                JAPANESE -> Locale.JAPAN
                PORTUGUESE -> Locale.forLanguageTag(PORTUGUESE)
                INDIA -> Locale.forLanguageTag(INDIA)
                VIETNAMESE -> Locale.forLanguageTag(VIETNAMESE)
                else -> Locale.KOREA
            }
        } else {
            Locale.getDefault()
        }
    }
//
    fun setCurrentLocale(languageTag: String) //for setting manually
            = preferenceManager.setData(LANGUAGE, languageTag)

    fun changeResourcesLocale() {
        val resources = application.resources
        val config = resources.configuration
        Locale.setDefault(getCurrentLocale())
        config.setLocale(getCurrentLocale())
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun viewEvent(content: Any) { _viewEvent.value = Event(content) }
    // for IO thread
    fun postViewEvent(content: Any) { _viewEvent.postValue(Event(content)) }

    inner class Event<out T>(private val content: T) {

        var hasBeenHandled = false
            private set // Allow external read but not write

        /**
         * Returns the content and prevents its use again.
         */
        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                content
            }
        }

        /**
         * Returns the content, even if it's already been handled.
         */
        fun peekContent(): T = content
    }

    //참고 https://leveloper.tistory.com/184 to prevent unnecessary null check of getValue
    open class NonNullLiveData<T: Any>(value: T): LiveData<T>(value) {
        override fun getValue(): T {
            return super.getValue() as T
        }

        inline fun observe(owner: LifecycleOwner, crossinline observer: (t: T) -> Unit) {
            this.observe(owner, Observer {
                it?.let(observer)
            })
        }
    }

    class NonNullMutableLiveData<T: Any>(value: T): NonNullLiveData<T>(value) {
        public override fun setValue(value: T) {
            super.setValue(value)
        }

        public override fun postValue(value: T) {
            super.postValue(value)
        }
    }

    fun backClicked() = viewEvent(BACK)
}