package kr.foorun.uni_eat.base.view

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view : View, visibility : Boolean){
        view.visibility = if(visibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("textWatcher")
    fun setTextWatcher(textView: EditText, textWatcher: TextWatcher) {
        textView.addTextChangedListener(textWatcher)
    }

}