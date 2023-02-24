package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import kr.foorun.presentation.R
import java.util.*

class BaseTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(attrs)
    }

    @SuppressLint("Recycle")
    fun init(attrs: AttributeSet?) {
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTextView)
        val t = attributeArray.getString(R.styleable.BaseTextView_android_text) ?: ""
        text = t

        val c = attributeArray.getColor(R.styleable.BaseTextView_android_textColor,
        ContextCompat.getColor(context,R.color.LargeTextColor))
        setTextColor(c)

        val g = attributeArray.getInt(R.styleable.BaseTextView_android_gravity,TEXT_ALIGNMENT_CENTER)
        gravity = g

//
//        val font = FontEnum.values()[(attributeArray.getString(R.styleable.BaseTextView_textFont) ?: "0").toInt()].name
//
//        if (textLocale == Locale.KOREA) {
//            typeface = Typeface.createFromAsset(context.assets,"fonts/NotoSansKR-$font-Hestia.otf")
//            includeFontPadding = false
//        } else {
//            typeface = Typeface.createFromAsset(context.assets, "fonts/Roboto-$font.ttf")
//        }
    }
}