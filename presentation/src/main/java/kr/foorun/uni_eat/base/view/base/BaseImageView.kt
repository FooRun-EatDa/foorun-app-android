package kr.foorun.uni_eat.base.view.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BaseImageView : AppCompatImageView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {

    }

    companion object {
        @JvmStatic
        @BindingAdapter("glide")
        fun setImage(view : BaseImageView , image : String){
            Glide.with(view.context)
                .load(image)
                .centerCrop()
//                .placeholder() //todo add preImage before loaded image
//                .error() //todo add an image in case of an error
                .into(view)
        }
    }
}