package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
import kr.foorun.uni_eat.databinding.BaseConstraintBinding

class BaseConstraintView : ConstraintLayout {

    private val binding : BaseConstraintBinding =
        BaseConstraintBinding.inflate(LayoutInflater.from(context), this, true)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseConstraintView)

        val src = typedArray.getResourceId(R.styleable.BaseConstraintView_android_src,R.drawable.back)
        binding.backBTN.setBackgroundResource(src)

        val backgroundColor = typedArray.getResourceId(R.styleable.BaseConstraintView_android_background,R.color.white)
        binding.root.setBackgroundResource(backgroundColor)
        typedArray.recycle()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setViewModel")
        fun setViewModel(view: BaseConstraintView, baseViewModel: BaseViewModel) {
            view.binding.run {
                viewModel = baseViewModel
                backBTN.setOnClickListener{ baseViewModel.backClicked() }
            }
        }
    }
}