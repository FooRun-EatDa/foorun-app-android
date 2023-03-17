package kr.foorun.uni_eat.base.view.base.view_pager2

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.view.base.recycler.indicator.CircleIndicator

class BaseViewPager2: ConstraintLayout {

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

        val root = findViewById<ConstraintLayout>(R.id.root)
        val viewPager = findViewById<ViewPager2>(R.id.detail_recycler)
        val indicator = findViewById<CircleIndicator>(R.id.viewpager_indicator)

        val rootColor = typedArray.getResourceId(
            R.styleable.BaseArticleContentConstraintView_root,
            R.color.invisible)

        val childSrc = typedArray.getResourceId(
            R.styleable.BaseArticleContentConstraintView_child,
            R.drawable.article_content_page)

        root.setBackgroundResource(rootColor)
        child.setBackgroundResource(childSrc)

        typedArray.recycle()
    }

}