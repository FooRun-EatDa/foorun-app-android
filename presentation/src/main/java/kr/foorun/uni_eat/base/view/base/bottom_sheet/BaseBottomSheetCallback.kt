package kr.foorun.uni_eat.base.view.base.bottom_sheet

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.presentation.R


class BaseBottomSheetCallback(
    private val behavior: BottomSheetBehavior<out View>,
    private val rootView: View,
    private val containerView: View,
    private val collapseView: View,
    private val expandView: View?,
    private val stateCallBack: (state : Int) -> Unit,
    private val rootClickable: Boolean = false,
    private val isBlur: Boolean = false
) : BottomSheetBehavior.BottomSheetCallback() {

    private val context = rootView.context
    private val colorDrawable = ColorDrawable(ContextCompat.getColor(context, R.color.black_60))

    init {
        collapseView.alpha = 1f
        expandView?.run {
            alpha = 0f
            isInvisible = true
        }
        setBlur(isBlur)
        setupListener()
    }

    private fun setupListener() {
        if(rootClickable){
            rootView.setOnClickListener { behavior.state = BottomSheetBehavior.STATE_HIDDEN }
        } else rootView.isClickable = false

//        containerView.setOnClickListener {
//            // no-op
//        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        //슬라이드 될때 slideOffset => hide -1.0 ~ collapsed 0.0 ~ expended 1.0
//        setRootBackgroundColor(slideOffset) //to make dark background of root-view
        setCollapseVisible(slideOffset)
    }

    /**
     *  make dark or bright background of root-view base on bottomSheet's state
     */
    private fun setRootBackgroundColor(slideOffset: Float) {
        val isCollapsed = slideOffset == (-1.0).toFloat()
        if (isCollapsed) setBlur(false)
        else setBlur(true)
    }

    /**
     *  to make dark background of root-view
     */
    private fun setBlur(toBlur: Boolean){
        if(toBlur && expandView == null){
            val blur = (COLOR_ALPHA_RATIO * 0.48).toInt()
            colorDrawable.alpha = blur
            rootView.setBackgroundColor(colorDrawable.color)
        } else {
            colorDrawable.alpha = 0
            rootView.setBackgroundColor(colorDrawable.color)
        }
    }

    private fun setCollapseVisible(slideOffset: Float) {
        val visibleOffset = (1 - slideOffset).coerceIn(0f, 1f)
        collapseView.alpha = visibleOffset
        expandView?.alpha = slideOffset
    }

    override fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {

        val isCollapseInvisible = newState == BottomSheetBehavior.STATE_EXPANDED
        val isExpandInvisible = newState == BottomSheetBehavior.STATE_COLLAPSED
        val isHidden = (newState == BottomSheetBehavior.STATE_HIDDEN)

        rootView.isClickable = newState == BottomSheetBehavior.STATE_EXPANDED
        collapseView.isInvisible = isCollapseInvisible
        expandView?.isInvisible = isExpandInvisible
        if (isBlur) setBlur(!isHidden)

        stateCallBack(newState)
    }

    companion object {
        private const val COLOR_ALPHA_RATIO = 255f
    }
}
