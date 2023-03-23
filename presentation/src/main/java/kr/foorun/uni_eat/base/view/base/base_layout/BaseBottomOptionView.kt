package kr.foorun.uni_eat.base.view.base.base_layout

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kr.foorun.presentation.R

class BaseBottomOptionView: ConstraintLayout {

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
        val v = li.inflate(R.layout.base_bottom_option, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseBottomOptionView)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        val root = findViewById<ConstraintLayout>(R.id.bottom_constraint)
        val option1 = findViewById<BaseImageView>(R.id.option1)
        val option2 = findViewById<BaseImageView>(R.id.option2)

        val visible = typedArray.getBoolean(R.styleable.BaseBottomOptionView_bottomVisible,true)
        val image1 = typedArray.getResourceId(
            R.styleable.BaseBottomOptionView_option1_image,
            R.drawable.share)

        val image2 = typedArray.getResourceId(
            R.styleable.BaseBottomOptionView_option2_image,
            R.drawable.bookmark)

        root.visibility = if(visible) View.VISIBLE else View.GONE
        option1.setBackgroundResource(image1)
        option2.setBackgroundResource(image2)

        typedArray.recycle()
    }
}