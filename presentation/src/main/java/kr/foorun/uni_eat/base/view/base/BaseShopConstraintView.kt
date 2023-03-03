package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R

class BaseShopConstraintView: ConstraintLayout {

    lateinit var name: BaseTextView
    lateinit var nick: BaseTextView
    lateinit var kind: BaseTextView
    lateinit var content: BaseTextView
    lateinit var viewBar: View

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

        name = findViewById<BaseTextView>(R.id.shop_name_text)
        nick = findViewById<BaseTextView>(R.id.shop_nick_text)
        kind = findViewById<BaseTextView>(R.id.shop_kind_text)
        content = findViewById<BaseTextView>(R.id.content)
        viewBar = findViewById<View>(R.id.vertical_line_view)

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

    fun setName(str: String){
        name.text = str
    }

    fun setNick(str: String){
        if(str.isNotBlank()) nick.text = str
        else {
            nick.visibility = View.GONE
            viewBar.visibility = View.GONE
        }
    }

    fun setKind(str: String){
       kind.text = str
    }

    fun setContent(str: String){
        content.text = str
    }

    companion object{
        @JvmStatic
        @BindingAdapter("setName")
        fun setName(view: BaseShopConstraintView, name: String?){
            name?.let { view.setName(it) }
        }

        @JvmStatic
        @BindingAdapter("setNick")
        fun setNick(view: BaseShopConstraintView, nick: String?){
            nick?.let { view.setNick(it) }
        }

        @JvmStatic
        @BindingAdapter("setContent")
        fun setContent(view: BaseShopConstraintView, content: String?){
            content?.let { view.setContent(it) }
        }

        @JvmStatic
        @BindingAdapter("setKind")
        fun setKind(view: BaseShopConstraintView, kind: String?){
            kind?.let { view.setKind(it) }
        }
    }
}