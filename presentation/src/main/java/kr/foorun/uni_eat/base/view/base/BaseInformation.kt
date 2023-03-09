package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R

class BaseInformation : ConstraintLayout {

    lateinit var informationValue: BaseTextView

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
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseInformation)

        val c = attributeArray.getColor(
            R.styleable.BaseInformation_android_textColor,
            ContextCompat.getColor(context, R.color.large_text)
        )

        val info = attributeArray.getString(R.styleable.BaseInformation_information) ?: ""
        val value = attributeArray.getString(R.styleable.BaseInformation_value) ?: ""
        val show = attributeArray.getBoolean(R.styleable.BaseInformation_show_arrow,false)

        val information = findViewById<BaseTextView>(R.id.information)
        informationValue = findViewById<BaseTextView>(R.id.information_value)
        val informationImage = findViewById<BaseImageView>(R.id.information_image)

        information.run {
            setTextColor(c)
            text = info
        }

        informationValue.run {
            setTextColor(c)
            text = value
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

    companion object{
        @JvmStatic
        @BindingAdapter("setInformationValue")
        fun setValue(view: BaseInformation, str: String?){
            str?.let { view.setInformationText(str) }
        }
    }
}
