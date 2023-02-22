package kr.foorun.uni_eat.base.view.base.shop_detail.indicator

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.LinearLayout


class CircleIndicator : LinearLayout {
    private var mContext: Context

    //원 사이의 간격
    private var itemMargin = 15

    //애니메이션 시간
    private var animDuration = 250
    private var mDefaultCircle = 0
    private var mSelectCircle = 0
    private lateinit var imageDot: ArrayList<BaseIndicatorLayout>

    fun setAnimDuration(animDuration: Int) {
        this.animDuration = animDuration
    }

    fun setItemMargin(itemMargin: Int) {
        this.itemMargin = itemMargin
    }

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
    }

    /**
     * 기본 점 생성
     * @param count 점의 갯수
     * @param defaultCircle 점의 이미지
     */
    fun createIndicator(count: Int) {
        imageDot = ArrayList()
        for (i in 0 until count) {
            imageDot.add(BaseIndicatorLayout(mContext).apply { init(i+1,false) })  //todo custom imageView (context) .apply{ number(1) }
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.marginEnd = itemMargin
            imageDot[i].layoutParams = params
            this.addView(imageDot[i])
        }
//        첫인덱스 선택
        select(0)
    }

    /**
     * 선택된 점 표시
     * @param position
     */
    fun select(position: Int) {
        if(position >= imageDot.size){
            imageDot[imageDot.size-1].onSelect(position+1)
        } else {
            for (i in imageDot.indices) {
                if (i == position) imageDot[i].onSelect(i+1)
                else  imageDot[i].onUnSelect(i+1)
            }
        }
    }

    /**
     * 선택된 점의 애니메이션
     * @param view
     * @param startScale
     * @param endScale
     */
    private fun selectScaleAnim(view: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            startScale, endScale,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = animDuration.toLong()
        view.startAnimation(anim)
        view.setTag(view.id, true)
    }

    /**
     * 선택되지 않은 점의 애니메이션
     * @param view
     * @param startScale
     * @param endScale
     */
    private fun defaultScaleAnim(view: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            startScale, endScale,
            startScale, endScale,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.fillAfter = true
        anim.duration = animDuration.toLong()
        view.startAnimation(anim)
        view.setTag(view.id, false)
    }
}