package kr.foorun.uni_eat.base.view.base.shop_detail

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.BaseInformationBinding

class BaseInformation : ConstraintLayout {

    private val binding: BaseInformationBinding =
        BaseInformationBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    fun init(attrs: AttributeSet?) {

        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseInformation)

        val c = attributeArray.getColor(
            R.styleable.BaseInformation_android_textColor,
            ContextCompat.getColor(context, R.color.LargeTextColor)
        )

        val info = attributeArray.getString(R.styleable.BaseInformation_information)
        val value = attributeArray.getString(R.styleable.BaseInformation_value)
        val show = attributeArray.getBoolean(R.styleable.BaseInformation_show_arrow,false)

        binding.information.run {
            setTextColor(c)
            text = info
        }
        binding.informationValue.run {
            setTextColor(c)
            text = value
        }

        if(show){
            binding.informationValue.visibility = GONE
            binding.informationImage.visibility = VISIBLE
        } else {
            binding.informationValue.visibility = VISIBLE
            binding.informationImage.visibility = GONE
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setRedUnderLine(red: Boolean) {
        backgroundTintList = if (red) ContextCompat.getColorStateList(context, R.color.black)
        else ContextCompat.getColorStateList(context, R.color.black)
    }
}
