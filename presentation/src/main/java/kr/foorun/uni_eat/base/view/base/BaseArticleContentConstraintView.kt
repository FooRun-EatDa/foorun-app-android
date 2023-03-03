package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kr.foorun.presentation.R

class BaseArticleContentConstraintView: ConstraintLayout {

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
        val v = li.inflate(R.layout.base_article_content_constraint, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseArticleContentConstraintView)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        val root = findViewById<ConstraintLayout>(R.id.root)
        val child = findViewById<ConstraintLayout>(R.id.child)

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