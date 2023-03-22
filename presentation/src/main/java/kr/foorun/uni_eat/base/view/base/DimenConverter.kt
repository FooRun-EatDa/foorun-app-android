package kr.foorun.uni_eat.base.view.base

import android.content.res.Resources
import android.util.TypedValue
import android.view.View

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun View.measuredHeight(): Int {
    this.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    return this.measuredHeight
}

fun View.measuredWidth(): Int {
    this.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    return this.measuredWidth
}