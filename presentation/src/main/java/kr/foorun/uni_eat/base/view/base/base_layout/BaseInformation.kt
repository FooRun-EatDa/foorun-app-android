package kr.foorun.uni_eat.base.view.base.base_layout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R

class BaseInformation : ConstraintLayout {

    lateinit var information: BaseTextView
    lateinit var informationValue: BaseTextView
    lateinit var informationImage: BaseImageView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        init(attrs)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v = li.inflate(R.layout.base_information, this, false)
        addView(v)
    }

    @SuppressLint("Recycle")
    fun init(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseInformation)

        val c = typedArray.getColor(
            R.styleable.BaseInformation_android_textColor,
            ContextCompat.getColor(context, R.color.large_text)
        )

        val info = typedArray.getString(R.styleable.BaseInformation_information) ?: ""
        val value = typedArray.getString(R.styleable.BaseInformation_value) ?: ""
        val show = typedArray.getBoolean(R.styleable.BaseInformation_show_arrow,false)
        val attrSize = typedArray.getDimensionPixelSize(R.styleable.BaseInformation_android_textSize,context.resources.getDimensionPixelSize(R.dimen.text_size_large_15))
        val image = typedArray.getResourceId(R.styleable.BaseInformation_android_src,R.drawable.arrow_down)
        val style = typedArray.getInt(R.styleable.BaseInformation_android_textStyle,Typeface.NORMAL)

        information = findViewById<BaseTextView>(R.id.information)
        informationValue = findViewById<BaseTextView>(R.id.information_value)
        informationImage = findViewById<BaseImageView>(R.id.information_image)

        information.run {
            setTextColor(c)
            text = info
            setTextSize(TypedValue.COMPLEX_UNIT_PX, attrSize.toFloat())
            setTypeface(typeface, style)
        }

        informationValue.run {
            setTextColor(c)
            text = value
            setTextSize(TypedValue.COMPLEX_UNIT_PX, attrSize.toFloat())
            setTypeface(typeface, style)
        }

        informationImage.run {
            setBackgroundResource(image)
        }

        if(show){
            informationValue.visibility = GONE
            informationImage.visibility = VISIBLE
        } else {
            informationValue.visibility = VISIBLE
            informationImage.visibility = GONE
        }
    }

    fun setInformationText(str: String){
        informationValue.text = str
    }

    fun setImageWidth(width: Int) {
        informationImage.layoutParams.width = width
    }
    fun setImageHeight(height: Int) {
        informationImage.layoutParams.height = height
    }

    companion object{
        @JvmStatic
        @BindingAdapter("setInformationValue")
        fun setValue(view: BaseInformation, str: String?){
            str?.let { view.setInformationText(str) }
        }

        @JvmStatic
        @BindingAdapter("setInfoImageWidth")
        fun setWidth(view: BaseInformation, width: Float?){
            width?.let { view.setImageWidth(it.toInt()) }
        }

        @JvmStatic
        @BindingAdapter("setInfoImageHeight")
        fun setHeight(view: BaseInformation, height: Float?){
            height?.let { view.setImageHeight(it.toInt()) }
        }
    }
}
