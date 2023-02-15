package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.databinding.BaseEdittextBinding

class BaseEditTextLayout : ConstraintLayout {

    private val binding : BaseEdittextBinding =
        BaseEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    fun init(attrs: AttributeSet?) {
        binding.edittext.isSingleLine = true

        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseEditTextLayout)

        val inputType = attributeArray.getInt(R.styleable.BaseEditTextLayout_android_inputType, EditorInfo.TYPE_NULL)

        if(inputType != EditorInfo.TYPE_NULL) {
            binding.edittext.inputType = inputType
        }

        val hint = attributeArray.getString(R.styleable.BaseEditTextLayout_android_hint) ?: ""
        binding.edittext.hint = hint

        val c = attributeArray.getColor(R.styleable.BaseTextView_android_textColor,
            ContextCompat.getColor(context,R.color.LargeTextColor))

        binding.edittext.setTextColor(c)

//        val font = FontEnum.values()[(attributeArray.getString(R.styleable.BaseEditText_textFont) ?: "0").toInt()].name
//
//        if (textLocale == Locale.KOREA) {
//            typeface = Typeface.createFromAsset(context.assets,"fonts/NotoSansKR-$font-Hestia.otf")
//            includeFontPadding = false
//        } else {
//            typeface = Typeface.createFromAsset(context.assets, "fonts/Roboto-$font.ttf")
//        }
    }

    fun setTextWatcher(textWatcher: TextWatcher){
        binding.edittext.addTextChangedListener(textWatcher)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setRedUnderLine(red: Boolean){
        backgroundTintList = if(red) ContextCompat.getColorStateList(context,R.color.black)
        else ContextCompat.getColorStateList(context,R.color.black)
    }

    companion object{
        @JvmStatic
        @BindingAdapter("textWatcher")
        fun setTextWatcher(textView: BaseEditTextLayout, textWatcher: TextWatcher) {
            textView.setTextWatcher(textWatcher)
        }
    }
}