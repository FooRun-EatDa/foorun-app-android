package kr.foorun.uni_eat.base.view.base.base_layout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import kr.foorun.presentation.R

class BaseEditTextView : AppCompatEditText {

    constructor(context: Context?) : super(context!!)
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(attrs)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(attrs)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Recycle", "ResourceType")
    fun init(attrs: AttributeSet?) {
        highlightColor = ContextCompat.getColor(context, R.color.gray3)
        textCursorDrawable = ContextCompat.getDrawable(context,R.drawable.cursor)
        ContextCompat.getDrawable(context,R.drawable.edittext_selector_color)
            ?.let {
                setTextSelectHandleLeft(it)
                setTextSelectHandleRight(it)
                setTextSelectHandle(it) }
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