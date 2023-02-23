package kr.foorun.uni_eat.base.view.binding

import android.text.TextWatcher
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.view.base.BaseEditTextLayout

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view : View, visibility : Boolean){
        view.visibility = if(visibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("topAnimVisible")
    fun setAnimVisible(view: View, visibility: Boolean){
        val transition: Transition = Slide(Gravity.TOP)
        transition.duration = 600
        transition.addTarget(view)
        TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
        view.visibility = if (visibility) View.VISIBLE else View.GONE
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

    @JvmStatic
    @BindingAdapter("textWatcher")
    fun setTextWatcher(baseEditTextLayout: BaseEditTextLayout, textWatcher: TextWatcher) {
        baseEditTextLayout.setTextWatcher(textWatcher)
    }

    @JvmStatic
    @BindingAdapter("searchWatcher")
    fun setTextWatcher(searchView: SearchView, textWatcher: SearchView.OnQueryTextListener) {
        searchView.setOnQueryTextListener(textWatcher)
    }

}