package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel

class BaseBarConstraintView : ConstraintLayout{

    constructor(context: Context) : super(context) {
        initView()
    }
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
        val v = li.inflate(R.layout.base_bar_constraint, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseBarConstraintView)
        init(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseBarConstraintView, defStyle, 0)
        init(typedArray)
    }

    fun init(typedArray: TypedArray) {

        val frontSrc = typedArray.getResourceId(R.styleable.BaseBarConstraintView_frontImage, R.drawable.arrow_left)
        val frontVisible = typedArray.getBoolean(R.styleable.BaseBarConstraintView_frontVisible,false)

        val rearSrc = typedArray.getResourceId(R.styleable.BaseBarConstraintView_rearImage, R.drawable.search_icon)
        val rearVisible = typedArray.getBoolean(R.styleable.BaseBarConstraintView_rearVisible,false)

        val titleSrc = typedArray.getResourceId(R.styleable.BaseBarConstraintView_frontImage, R.drawable.bar_image)
        val titleImageVisible = typedArray.getBoolean(R.styleable.BaseBarConstraintView_tittleImageVisible,false)

        val titleText = typedArray.getString(R.styleable.BaseBarConstraintView_titleText) ?: ""
        val titleTextVisible = typedArray.getBoolean(R.styleable.BaseBarConstraintView_titleTextVisible,true)

        val barColor = typedArray.getResourceId(R.styleable.BaseBarConstraintView_barColor,R.color.white)

        val frontImage = findViewById<BaseImageView>(R.id.front_image)
        val rearImage = findViewById<BaseImageView>(R.id.rear_image)
        val barImage = findViewById<BaseImageView>(R.id.bar_image)
        val barTitle = findViewById<BaseTextView>(R.id.bar_title)
        val barConstraint = findViewById<ConstraintLayout>(R.id.bar_constraint)

        if(frontVisible){
            frontImage.setBackgroundResource(frontSrc)
            frontImage.visibility = View.VISIBLE
        } else frontImage.visibility = View.GONE

        if(rearVisible) {
            rearImage.setBackgroundResource(rearSrc)
            rearImage.visibility = View.VISIBLE
        } else rearImage.visibility = View.GONE

        if(titleImageVisible){
            barImage.setBackgroundResource(titleSrc)
            barImage.visibility = View.VISIBLE
        } else barImage.visibility = View.GONE

        if(titleTextVisible){
            barTitle.text = titleText
            barTitle.visibility = View.VISIBLE
        } else barTitle.visibility = View.GONE

        barConstraint.setBackgroundResource(barColor)

        typedArray.recycle()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setViewModel")
        fun setViewModel(view: BaseBarConstraintView, baseViewModel: BaseViewModel) {
//            view.binding.run {
//                viewModel = baseViewModel
//                frontImage.setOnClickListener{ baseViewModel.backClicked() }
//            }
        }
    }
}