package kr.foorun.uni_eat.base.view.binding

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.uni_eat.R

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

    @JvmStatic
    @BindingAdapter("setTagDrawable")
    fun setDrawable(view: View, isPicked : Boolean) {
        if(!isPicked) view.background = ContextCompat.getDrawable(view.context, R.drawable.radius_gray_25)
        else view.background = ContextCompat.getDrawable(view.context, R.drawable.radius_black_25)
    }

    @JvmStatic
    @BindingAdapter("setTagTextColor")
    fun setTextColor(view: TextView, isPicked : Boolean) {
        if(!isPicked) view.setTextColor(ContextCompat.getColor(view.context,R.color.black))
        else view.setTextColor(ContextCompat.getColor(view.context,R.color.white))
    }


}