package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import kr.foorun.presentation.R

class BaseArticleImage: ConstraintLayout {

    lateinit var shopImage: BaseImageView
    lateinit var shopArea: BaseTextView
    lateinit var shopName: BaseTextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
        getAttrs(attrs)
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v = li.inflate(R.layout.base_artice_image, this, false)
        addView(v)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseArticleImage)
        init(typedArray)
    }

    private fun init(typedArray: TypedArray) {

        shopImage = findViewById<BaseImageView>(R.id.image)
        shopName = findViewById<BaseTextView>(R.id.shop_name)
        shopArea = findViewById<BaseTextView>(R.id.shop_area)

        val area = typedArray.getString(R.styleable.BaseArticleImage_articleShopArea) ?: ""
        val name = typedArray.getString(R.styleable.BaseArticleImage_articleShopName) ?: ""

        shopArea.text = area
        shopName.text = name

        typedArray.recycle()
    }

    fun setImage(image: String){
        shopImage.glide(image)
    }

    fun setName(name: String){
        shopName.text = name
    }

    fun setArea(area: String){
        shopArea.text = area
    }

    companion object{
        @JvmStatic
        @BindingAdapter("setShopImage")
        fun setImage(view: BaseArticleImage, image: String?){
            image?.let { view.setImage(it) }
        }

        @JvmStatic
        @BindingAdapter("setShopName")
        fun setName(view: BaseArticleImage, name: String?){
            name?.let { view.setName(it) }
        }

        @JvmStatic
        @BindingAdapter("setShopArea")
        fun setArea(view: BaseArticleImage, area: String?){
            area?.let { view.setArea(it) }
        }
    }
}