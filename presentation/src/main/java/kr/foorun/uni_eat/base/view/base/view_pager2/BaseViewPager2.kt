package kr.foorun.uni_eat.base.view.base.view_pager2

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.view.base.recycler.indicator.CircleIndicator
import kr.foorun.uni_eat.feature.map.shop_detail.adapter.viewpager.ShopImageAdapter

class BaseViewPager2: ConstraintLayout {

    lateinit var root : ConstraintLayout
    lateinit var viewPager : ViewPager2
    lateinit var indicator : CircleIndicator

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
        val v = li.inflate(R.layout.base_view_pager2, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseViewPager2)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        root = findViewById<ConstraintLayout>(R.id.root)
        viewPager = findViewById<ViewPager2>(R.id.detail_recycler)
        indicator = findViewById<CircleIndicator>(R.id.viewpager_indicator)

        val bg = typedArray.getResourceId(R.styleable.BaseViewPager2_android_background,R.color.white)
        root.setBackgroundResource(bg)

        typedArray.recycle()
    }

    fun setIndicator(numberCount: Int) = indicator.createIndicator(numberCount)
    fun setPager(adapter: ShopImageAdapter) {
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                indicator.select(position)
            }
        })
    }
    fun setPagerWidth(width: Int) {
        viewPager.layoutParams.width = width
    }
    fun setPagerHeight(height: Int) {
        viewPager.layoutParams.height = height
    }

    companion object{
        @JvmStatic
        @BindingAdapter("setIndicator")
        fun setIndicator(view: BaseViewPager2, numberCount: Int?){
            numberCount?.let { view.setIndicator(it) }
        }

        @JvmStatic
        @BindingAdapter("setAdapter")
        fun setAdapter(view: BaseViewPager2, adapter: ShopImageAdapter?){
            adapter?.let { view.setPager(it) }
        }

        @JvmStatic
        @BindingAdapter("setPagerWidth")
        fun setWidth(view: BaseViewPager2, width: Float?){
            width?.let { view.setPagerWidth(it.toInt()) }
        }

        @JvmStatic
        @BindingAdapter("setPagerHeight")
        fun setHeight(view: BaseViewPager2, height: Float?){
            height?.let { view.setPagerHeight(it.toInt()) }
        }

    }
}