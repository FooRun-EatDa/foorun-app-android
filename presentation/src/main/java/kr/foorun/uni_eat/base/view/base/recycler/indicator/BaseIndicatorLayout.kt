package kr.foorun.uni_eat.base.view.base.recycler.indicator

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.BaseIndicatorBinding

class BaseIndicatorLayout : ConstraintLayout {

    private val binding : BaseIndicatorBinding =
        BaseIndicatorBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @SuppressLint("SetTextI18n")
    fun init(number: Int, isSelected: Boolean) {
        binding.run {
            indicatorNumber.text = getNumberFormat(number)
            if(isSelected){
                indicatorLine.visibility = View.VISIBLE
                indicatorNumber.setTextColor(ContextCompat.getColor(context, R.color.red))
            } else {
                indicatorLine.visibility = View.GONE
                indicatorNumber.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }
        invalidate()
    }

    fun onSelect(){
        binding.run {
            indicatorLine.visibility = View.VISIBLE
            indicatorNumber.setTextColor(ContextCompat.getColor(context, R.color.red))
        }
    }

    @SuppressLint("SetTextI18n")
    fun onSelect(number: Int){
        binding.run {
            indicatorLine.visibility = View.VISIBLE
            indicatorNumber.setTextColor(ContextCompat.getColor(context, R.color.red))
            indicatorNumber.text = getNumberFormat(number)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onUnSelect(number: Int){
        binding.run {
            indicatorLine.visibility = View.GONE
            indicatorNumber.setTextColor(ContextCompat.getColor(context, R.color.white))
            indicatorNumber.text = getNumberFormat(number)
        }
    }

    private fun getNumberFormat(number: Int)
    = if(number < 10) "$number" else "$number"
}