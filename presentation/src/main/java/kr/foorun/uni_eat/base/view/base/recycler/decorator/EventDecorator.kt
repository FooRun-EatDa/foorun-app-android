package kr.foorun.uni_eat.base.view.base.recycler.decorator

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.uni_eat.base.view.base.dpToPx

class EventDecorator(val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val dp7 = 7.dpToPx(context)
        outRect.set(dp7, 0, 0, dp7)
    }
}