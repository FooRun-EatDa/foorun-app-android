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
    private val stateCallBack: (state : Int) -> Unit
) : BottomSheetBehavior.BottomSheetCallback() {

    private val context = rootView.context
    private val colorDrawable = ColorDrawable(ContextCompat.getColor(context, R.color.black_60))

    init {
        collapseView.alpha = 1f
        expandView?.run {
            alpha = 0f
            isInvisible = true
        }
        setBlur(true)
        setupListener()
        rootView.isClickable = false
    }

    private fun setupListener() {
//        rootView.setOnClickListener {
//            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
//                behavior.state = BottomSheetBehavior.STATE_HIDDEN
//            }
//        }
        containerView.setOnClickListener {
            // no-op
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        //슬라이드 될때 slideOffset => hide -1.0 ~ collapsed 0.0 ~ expended 1.0
        setRootBackgroundColor(slideOffset)
        setCollapseVisible(slideOffset)
    }

    private fun setRootBackgroundColor(slideOffset: Float) {
        val isCollapsed = slideOffset == (-1.0).toFloat()
        if (isCollapsed) setBlur(false)
        else setBlur(true)
    }

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

        rootView.isClickable = newState == BottomSheetBehavior.STATE_EXPANDED
        collapseView.isInvisible = isCollapseInvisible
        expandView?.isInvisible = isExpandInvisible

        stateCallBack(newState)
    }

    companion object {
        private const val COLOR_ALPHA_RATIO = 255f
    }
}
