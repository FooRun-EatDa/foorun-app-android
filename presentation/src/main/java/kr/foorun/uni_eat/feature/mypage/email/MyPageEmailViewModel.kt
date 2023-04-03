package kr.foorun.uni_eat.feature.mypage.email

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.MutableEventFlow
import kr.foorun.uni_eat.base.viewmodel.asEventFlow
import javax.inject.Inject

class MyPageEmailViewModel @Inject constructor(
): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<EmailEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    private val _underLine = MutableStateFlow(true)
    val underLine = _underLine.asLiveData()

    private val _emailText = MutableStateFlow("")
    val emailText = _emailText.asLiveData()

    private val _emailCheck = MutableStateFlow<EmailCase>(EmailCase.Nothing())
    val emailCheck = _emailCheck.asLiveData()

    val emailTextWatcher = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                _emailText.value = it
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    fun setUnderLine(isBlack: Boolean) = viewModelScope.launch { _underLine.emit(isBlack) }

//    fun setEmailCheck() = viewModelScope.launch {
//        if(_emailText.value.isNotEmpty()) _emailCheck.value = EmailWrongCase.Success()
//    }

    private fun checkEmail() {
        //todo wait for server is following up us
    }

    sealed class EmailEvent{}

    sealed class EmailCase{
        data class WrongEmail(val unit: Unit? = null): EmailCase()
        data class Success(val unit: Unit? = null): EmailCase()
        data class Nothing(val unit: Unit? = null): EmailCase()
    }

    private fun event(event: EmailEvent) = viewModelScope.launch { _eventFlow.emit(event) }
    private fun emailCaseEvent(event: EmailCase) = viewModelScope.launch { _emailCheck.emit(event) }

    fun doneClicked() {
        val email = _emailText.value
        setUnderLine(email.isNotBlank())
        if(email.isNotBlank()) emailCaseEvent(EmailCase.Success())
        else emailCaseEvent(EmailCase.WrongEmail())
    }
}