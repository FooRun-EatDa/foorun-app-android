package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kr.foorun.presentation.R

class BaseShopConstraintView: ConstraintLayout {

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
        val v = li.inflate(R.layout.base_shop_information_constraint, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseShopConstraintView)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray){

        val name = findViewById<BaseTextView>(R.id.shop_name_text)
        val nick = findViewById<BaseTextView>(R.id.shop_nick_text)
        val kind = findViewById<BaseTextView>(R.id.shop_kind_text)
        val content = findViewById<BaseTextView>(R.id.content)
        val viewBar = findViewById<View>(R.id.vertical_line_view)

        val nameText = typedArray.getString(R.styleable.BaseShopConstraintView_shopName) ?: ""
        val nickText = typedArray.getString(R.styleable.BaseShopConstraintView_shopNick) ?: ""
        val kindText = typedArray.getString(R.styleable.BaseShopConstraintView_shopKind) ?: ""
        val contentText = typedArray.getString(R.styleable.BaseShopConstraintView_shopContent) ?: ""

        name.text = nameText
        if(nickText.isNotBlank()) nick.text = nickText
        else {
            nick.visibility = View.GONE
            viewBar.visibility = View.GONE
        }
        kind.text = kindText
        content.text = contentText

        typedArray.recycle()
    }
}