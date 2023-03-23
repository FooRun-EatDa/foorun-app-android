package kr.foorun.uni_eat.base.view.base.base_layout

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel

class BaseConstraintView : ConstraintLayout {

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
        val v = li.inflate(R.layout.base_constraint, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseConstraintView)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        val root = findViewById<ConstraintLayout>(R.id.root)
        val backButton = findViewById<BaseImageView>(R.id.back_button)

        val src = typedArray.getResourceId(R.styleable.BaseConstraintView_android_src,R.drawable.back)
        backButton.setBackgroundResource(src)

        val backgroundColor = typedArray.getResourceId(R.styleable.BaseConstraintView_android_background,R.color.white)
        root.setBackgroundResource(backgroundColor)

        typedArray.recycle()
    }

    private fun onClick(action: () -> Unit) {
        val backButton = findViewById<BaseImageView>(R.id.back_button)
        backButton.setOnClickListener { action() }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setViewModel")
        fun setViewModel(view: BaseConstraintView, baseViewModel: BaseViewModel) {
            view.onClick { baseViewModel.backClicked() }
        }
    }
}