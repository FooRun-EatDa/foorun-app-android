package kr.foorun.uni_eat.base.view.base.recycler.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.EVENT_ITEM_MARGIN_BOTTOM
import kr.foorun.const.Constant.Companion.EVENT_ITEM_MARGIN_GAP
import kr.foorun.const.Constant.Companion.EVENT_ITEM_MARGIN_SIDE
import kr.foorun.const.Constant.Companion.SPAN_COUNT
import kr.foorun.uni_eat.base.view.base.dp

/**
 * please add values for int. it's gonna convert int-value you input into dp
 * default values:
 *
 * sideSpace == 21dp
 * gapSpace == 7dp
 * spanCount = 2
 * oriental = STAGGERED
 */
class EventDecorator(sideSpace: Int = EVENT_ITEM_MARGIN_SIDE, gapSpace: Int = EVENT_ITEM_MARGIN_GAP, bottomSpace: Int = EVENT_ITEM_MARGIN_BOTTOM, private val spanCount: Int = SPAN_COUNT, private val oriental: Int = STAGGERED) : RecyclerView.ItemDecoration() {

    companion object{
        const val VERTICAL = 0
        const val HORIZONTAL = 1
        const val GRID = 2
        const val STAGGERED = 3
    }

    private val sideDP = sideSpace.dp
    private val gapDP = gapSpace.dp
    private val bottomDP = bottomSpace.dp

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildAdapterPosition(view) //각 아이템뷰의 순서 (index)
        val totalItemCount = state.itemCount                //총 아이템 수
        //val scrollPosition = state.targetScrollPosition     //스크롤 됐을때 아이템 position


        when(oriental){
            VERTICAL -> setVertical(position, outRect, totalItemCount)
            HORIZONTAL -> setHorizontal(position, outRect, totalItemCount)
            GRID -> setGrid(position, outRect)
            STAGGERED -> setStaggered(position, outRect)
        }
    }

    private fun setStaggered(position: Int, outRect: Rect) = outRect.run {
        val column = position % spanCount + 1      // 1부터 시작
//        val row = ((position - 1) / spanCount) + 1

        bottom = bottomDP

        if (column == 1) {
            right = gapDP
            left = sideDP
        }

        if(column == spanCount) right = sideDP
    }

    private fun setVertical(position: Int, outRect: Rect, totalItemCount: Int) = outRect.run {
        set(gapDP, 0, gapDP, gapDP)
    }

    private fun setGrid(position: Int, outRect: Rect) {}
    private fun setHorizontal(position: Int, outRect: Rect, totalItemCount: Int) {}


}