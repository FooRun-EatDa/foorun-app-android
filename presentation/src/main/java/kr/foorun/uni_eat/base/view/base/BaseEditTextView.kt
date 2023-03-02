package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import kr.foorun.presentation.R

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
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseEditTextView)
        val c = attributeArray.getColor(R.styleable.BaseTextView_android_textColor,
            ContextCompat.getColor(context,R.color.large_text))
        setTextColor(c)

        val inputType = attributeArray.getInt(R.styleable.BaseEditTextLayout_android_inputType, EditorInfo.TYPE_NULL)

        if(inputType != EditorInfo.TYPE_NULL) {
            setInputType(inputType)
        }

        val hint = attributeArray.getString(R.styleable.BaseEditTextLayout_android_hint) ?: ""
        setHint(hint)
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