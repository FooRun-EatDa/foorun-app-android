package kr.foorun.uni_eat.feature.event

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        val dp7 = 7.dpToPx(context)
        val dp50 = 50.dpToPx(context)

        //2번 째 아이템일 때 상단여백 추가
        if (index == 2)
            outRect.set(dp7, dp50, 0, dp7)

        else
            outRect.set(dp7, 0, 0, dp7)
    }

    //픽셀 -> DP 로 변경
    fun Int.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
    }
}