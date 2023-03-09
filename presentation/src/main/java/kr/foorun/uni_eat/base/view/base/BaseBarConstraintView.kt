package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.feature.article.entire.ArticleEntireViewModel
import kr.foorun.uni_eat.feature.article.post.ArticlePostViewModel
import kr.foorun.uni_eat.feature.mypage.MyPageViewModel


class BaseBarConstraintView : ConstraintLayout{

    lateinit var frontImage : BaseImageView
    lateinit var rearImage : BaseImageView
    lateinit var barImage : BaseImageView
    lateinit var barTitle : BaseTextView
    lateinit var barConstraint : ConstraintLayout
    lateinit var frontFrame: FrameLayout
    lateinit var rearFrame: FrameLayout
    lateinit var barFrame: FrameLayout

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

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)

        frontFrame = findViewById(R.id.front_image_frame)
        rearFrame = findViewById(R.id.rear_image_frame)
        barFrame = findViewById(R.id.bar_image_frame)
        frontImage = findViewById<BaseImageView>(R.id.front_image)
        rearImage = findViewById<BaseImageView>(R.id.rear_image)
        barImage = findViewById<BaseImageView>(R.id.bar_image)
        barTitle = findViewById<BaseTextView>(R.id.bar_title)
        barConstraint = findViewById<ConstraintLayout>(R.id.bar_constraint)

        if(frontVisible){
            frontImage.setBackgroundResource(frontSrc)
            frontFrame.visibility = View.VISIBLE
        } else frontFrame.visibility = View.GONE

        if(rearVisible) {
            rearImage.setBackgroundResource(rearSrc)
            rearFrame.visibility = View.VISIBLE
        } else rearFrame.visibility = View.GONE

        if(titleImageVisible){
            barImage.setBackgroundResource(titleSrc)
            barFrame.visibility = View.VISIBLE
        } else barFrame.visibility = View.GONE

        if(titleTextVisible){
            barTitle.text = titleText
            barTitle.visibility = View.VISIBLE
        } else barTitle.visibility = View.GONE

        barConstraint.setBackgroundResource(barColor)

        typedArray.recycle()
    }

    private fun setRearOnClick(action: () -> Unit) = rearFrame.setOnClickListener { action() }
    private fun setFrontOnClick(action: () -> Unit) = frontFrame.setOnClickListener { action() }
    private fun setTitleImageOnClick(action: () -> Unit) = barImage.setOnClickListener { action() }

    companion object {

        @JvmStatic
        @BindingAdapter("setViewModel")
        fun setOnClicks(view: BaseBarConstraintView, vm: BaseViewModel) {
            view.run {
                when(vm){
                    is ArticleEntireViewModel -> setRearOnClick { vm.searchClick() }
                    is ArticlePostViewModel -> {}
                    is MyPageViewModel -> setRearOnClick{ vm.clickedMyPageMore() }
                }
                setFrontOnClick { vm.backClicked() }
                setTitleImageOnClick {  }
            }
        }
    }
}