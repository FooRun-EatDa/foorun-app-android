package kr.foorun.uni_eat.base.viewmodel


import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.foorun.data.local.PreferenceManager
import javax.inject.Inject

open class BaseViewModel: ViewModel(){

    @Inject protected lateinit var preferenceManager: PreferenceManager

    sealed class BaseEvent {
        data class Back(val unit: Unit? = null) : BaseEvent()
    }

    private val _viewBaseEvent = MutableEventFlow<BaseEvent>()
    val viewEvent = _viewBaseEvent.asEventFlow()

    fun backClicked() = viewModelScope.launch {
        _viewBaseEvent.emit(BaseEvent.Back(Unit))
    }

//    fun viewEvent(content: Any) { _viewEvent.value = Event(content) }
//    // for IO thread
//    fun postViewEvent(content: Any) { _viewEvent.postValue(Event(content)) }
//
//    inner class Event<out T>(private val content: T) {
//
//        var hasBeenHandled = false
//            private set // Allow external read but not write
//
//        /**
//         * Returns the content and prevents its use again.
//         */
//        fun getContentIfNotHandled(): T? {
//            return if (hasBeenHandled) {
//                null
//            } else {
//                hasBeenHandled = true
//                content
//            }
//        }
//
//        /**
//         * Returns the content, even if it's already been handled.
//         */
//        fun peekContent(): T = content
//    }
//
//    //참고 https://leveloper.tistory.com/184 to prevent unnecessary null check of getValue
//    open class NonNullLiveData<T: Any>(value: T): LiveData<T>(value) {
//        override fun getValue(): T {
//            return super.getValue() as T
//        }
//
//        inline fun observe(owner: LifecycleOwner, crossinline observer: (t: T) -> Unit) {
//            this.observe(owner, Observer {
//                it?.let(observer)
//            })
//        }
//    }
//
//    class NonNullMutableLiveData<T: Any>(value: T): NonNullLiveData<T>(value) {
//        public override fun setValue(value: T) {
//            super.setValue(value)
//        }
//
//        public override fun postValue(value: T) {
//            super.postValue(value)
//        }
//    }
//
//
}