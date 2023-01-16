package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import kr.foorun.uni_eat.R

class BaseEditTextView : AppCompatEditText {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("Recycle")
    fun init(attrs: AttributeSet?) {
        backgroundTintList = ContextCompat.getColorStateList(context, R.color.black)
//        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTextView)
//        val font = FontEnum.values()[(attributeArray.getString(R.styleable.BaseTextView_textFont) ?: "0").toInt()].name
//
//        if (textLocale == Locale.KOREA) {
//            typeface = Typeface.createFromAsset(context.assets,"fonts/NotoSansKR-$font-Hestia.otf")
//            includeFontPadding = false
//        } else {
//            typeface = Typeface.createFromAsset(context.assets, "fonts/Roboto-$font.ttf")
//        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setRedUnderLine(red: Boolean){
        backgroundTintList = if(red) ContextCompat.getColorStateList(context, R.color.black)
        else ContextCompat.getColorStateList(context, R.color.black)
    }
}