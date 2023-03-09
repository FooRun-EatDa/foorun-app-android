package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R

class BaseEditTextLayout : ConstraintLayout {

    lateinit var baseEditText: BaseEditTextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        getAttrs(attrs)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v = li.inflate(R.layout.base_edittext, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseEditTextLayout)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        baseEditText = findViewById<BaseEditTextView>(R.id.base_edittext)

        val attrIsSingle = typedArray.getBoolean(R.styleable.BaseEditTextLayout_android_singleLine,true)
        val attrInputType = typedArray.getInt(R.styleable.BaseEditTextLayout_android_inputType, EditorInfo.TYPE_NULL)
        val attrHint = typedArray.getString(R.styleable.BaseEditTextLayout_android_hint) ?: ""
        val attrColor = typedArray.getColor(R.styleable.BaseTextView_android_textColor,
            ContextCompat.getColor(context,R.color.large_text))
        val attrBackground = typedArray.getResourceId(R.styleable.BaseEditTextLayout_android_background,R.color.invisible)

        baseEditText.run {
            isSingleLine = attrIsSingle
            setTextColor(attrColor)
            if(attrInputType != EditorInfo.TYPE_NULL) {
                inputType = attrInputType
            }
            hint = attrHint
            setBackgroundResource(attrBackground)
        }

        typedArray.recycle()
    }

    fun setTextWatcher(textWatcher: TextWatcher){
        baseEditText.addTextChangedListener(textWatcher)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setRedUnderLine(red: Boolean){
        backgroundTintList = if(red) ContextCompat.getColorStateList(context,R.color.red)
        else ContextCompat.getColorStateList(context,R.color.black)
    }

    companion object{
        @JvmStatic
        @BindingAdapter("textWatcher")
        fun setTextWatcher(textView: BaseEditTextLayout, textWatcher: TextWatcher) {
            textView.setTextWatcher(textWatcher)
        }

        @JvmStatic
        @BindingAdapter("setUnderLine")
        fun setUnderLine(textView: BaseEditTextLayout, isRed: Boolean) {
            textView.setRedUnderLine(isRed)
        }
    }
}