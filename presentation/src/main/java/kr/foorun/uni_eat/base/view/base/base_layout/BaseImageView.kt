package kr.foorun.uni_eat.base.view.base.base_layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kr.foorun.presentation.R

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

    fun glide(image: String){
        Glide.with(context)
            .load(image)
            .centerCrop()
//                .placeholder() //todo add preImage before loaded image
//                .error() //todo add an image in case of an error
            .into(this)
    }

    fun glide(image: Drawable?){
        Glide.with(context)
            .load(image)
            .centerCrop()
//                .placeholder() //todo add preImage before loaded image
            .error(R.drawable.non_search_image) //todo add an image in case of an error
            .into(this)
    }

    fun glide(image: Int) {
        Glide.with(context)
            .load(image)
            .fitCenter()
//                .placeholder() //todo add preImage before loaded image
            .error(R.drawable.non_search_image) //todo add an image in case of an error
            .into(this)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("glide")
        fun setImage(view: BaseImageView, image: String?) {
            image?.let { view.glide(it) }
        }
    }
}