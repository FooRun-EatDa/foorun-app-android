package kr.foorun.uni_eat.base.view.base.base_layout

import android.content.Context
import android.content.res.TypedArray
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.Dimension
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.feature.mypage.edit.EditMyPageViewModel

class BaseEditTextLayout : ConstraintLayout {

    lateinit var baseEditText: BaseEditTextView
    lateinit var underLine: BaseImageView
    lateinit var rearImage: BaseImageView

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

        baseEditText = findViewById(R.id.base_edittext)
        underLine = findViewById(R.id.under_line)
        rearImage = findViewById(R.id.rear_image)

        val attrIsSingle = typedArray.getBoolean(R.styleable.BaseEditTextLayout_android_singleLine,true)
        val attrInputType = typedArray.getInt(R.styleable.BaseEditTextLayout_android_inputType, EditorInfo.TYPE_NULL)
        val attrHint = typedArray.getString(R.styleable.BaseEditTextLayout_android_hint) ?: ""
        val attrColor = typedArray.getColor(R.styleable.BaseEditTextLayout_android_textColor,
            ContextCompat.getColor(context,R.color.large_text))
        val attrBackground = typedArray.getResourceId(R.styleable.BaseEditTextLayout_android_background,R.color.invisible)
        val attrSize = typedArray.getDimensionPixelSize(R.styleable.BaseEditTextLayout_android_textSize,context.resources.getDimensionPixelSize(R.dimen.text_size_large_15))
        val attrHintColor = typedArray.getColor(R.styleable.BaseEditTextLayout_android_textColorHint, ContextCompat.getColor(context,R.color.gray3))
        val attrIsUnderLine = typedArray.getBoolean(R.styleable.BaseEditTextLayout_editUnderLineVisible, false)
        val attrUnderLineImage = typedArray.getResourceId(R.styleable.BaseEditTextLayout_underLineImage, R.drawable.under_line)
        val attrText = typedArray.getString(R.styleable.BaseEditTextLayout_android_text)
        val attrRearVisible = typedArray.getBoolean(R.styleable.BaseEditTextLayout_editRearVisible, false)
        val attrRearImage = typedArray.getResourceId(R.styleable.BaseEditTextLayout_editRearImage, R.drawable.check)

        baseEditText.run {
            isSingleLine = attrIsSingle
            setTextColor(attrColor)
            if(attrInputType != EditorInfo.TYPE_NULL) {
                inputType = attrInputType
            }
            hint = attrHint
            setHintTextColor(attrHintColor)
            setBackgroundResource(attrBackground)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, attrSize.toFloat())
            if(!attrText.isNullOrEmpty()) setText(attrText)
        }

        underLine.run {
            isVisible = attrIsUnderLine
            setBackgroundResource(attrUnderLineImage)
        }

        rearImage.run {
            isVisible = attrRearVisible
            setBackgroundResource(attrRearImage)
        }

        typedArray.recycle()
    }

    fun setTextWatcher(textWatcher: TextWatcher){
        baseEditText.addTextChangedListener(textWatcher)
    }

    fun setEditText(str: String){
        baseEditText.setText(str)
    }

    fun setRearOnClick(action: () -> Unit){
        rearImage.setOnClickListener { action() }
    }

    fun setUnderLineImage(isBlack: Boolean){
        if(isBlack) underLine.setBackgroundResource(R.drawable.under_line)
        else underLine.setBackgroundResource(R.drawable.under_line_red)
    }

    fun setUnderLineWidthMatch(){
        underLine.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
    }

    companion object{
        @JvmStatic
        @BindingAdapter("textWatcher")
        fun setTextWatcher(view: BaseEditTextLayout, textWatcher: TextWatcher) {
            view.setTextWatcher(textWatcher)
        }

        @JvmStatic
        @BindingAdapter("setEditText")
        fun setEditText(view: BaseEditTextLayout, str: String?){
            str?.let { view.setEditText(it) }
        }

        @JvmStatic
        @BindingAdapter("setRearOnClick")
        fun setRearOnClick(view: BaseEditTextLayout, action: () -> Unit){
            view.setRearOnClick(action)
        }

        @JvmStatic
        @BindingAdapter("setUnderLine")
        fun setUnderLine(view: BaseEditTextLayout, isBlack: Boolean?){
            isBlack?.let { view.setUnderLineImage(isBlack) }
        }

        @JvmStatic
        @BindingAdapter("setUnderLineMatch")
        fun setUnderLineMatch(view: BaseEditTextLayout, boolean: Boolean?){
            boolean?.let { view.setUnderLineWidthMatch() }
        }
    }
}