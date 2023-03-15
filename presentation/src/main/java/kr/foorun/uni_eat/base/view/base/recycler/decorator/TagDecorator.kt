package kr.foorun.uni_eat.base.view.base.recycler.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.SPAN_COUNT
import kr.foorun.const.Constant.Companion.TAG_ITEM_MARGIN_BOTTOM
import kr.foorun.const.Constant.Companion.TAG_ITEM_MARGIN_GAP
import kr.foorun.uni_eat.base.view.base.dp

/**
 * please add values for int. it's gonna convert int-value you input into dp
 *
 * default value for articles :
 *
 * sideSpace == 11dp
 * bottomSpace == 16dp
 * spanCount = 2
 * oriental = STAGGERED
 *
 * but anything can be created with parameter value and it's gonna be turned into dp value automatically in this code.
 */
class TagDecorator (gapSpace: Int = TAG_ITEM_MARGIN_GAP, bottomSpace: Int = TAG_ITEM_MARGIN_BOTTOM, private val spanCount: Int = SPAN_COUNT, private val oriental: Int = HORIZONTAL) : RecyclerView.ItemDecoration() {

    companion object{
        const val VERTICAL = 0
        const val HORIZONTAL = 1
        const val GRID = 2
    }

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
        }
    }

    private fun setGrid(position: Int, outRect: Rect) {
        val column = position % spanCount + 1      // 1부터 시작
        val row = ((position - 1) / spanCount) + 1

        outRect.run {
            if (row != spanCount) {
                bottom = bottomDP
                if (column != spanCount) right = gapDP
            }
        }
    }

    private fun setVertical(position: Int, outRect: Rect, totalItemCount: Int) {}

    private fun setHorizontal(position: Int, outRect: Rect, totalItemCount: Int) {
        if (position != 0) outRect.right = gapDP
        if (position != totalItemCount-1) outRect.right = gapDP
    }
}