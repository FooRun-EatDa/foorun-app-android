package kr.foorun.uni_eat.base.view.base.recycler.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.const.Constant.Companion.TAG_ITEM_MARGIN_BOTTOM
import kr.foorun.const.Constant.Companion.TAG_ITEM_MARGIN_RIGHT
import kr.foorun.uni_eat.base.view.base.dp

class TagDecorator (private val rightSpace: Int = TAG_ITEM_MARGIN_RIGHT, private val bottomSpace: Int = TAG_ITEM_MARGIN_BOTTOM, private val spanCount: Int? = null, private val oriental: Int = HORIZONTAL) : RecyclerView.ItemDecoration() {

    companion object{
        const val VERTICAL = 0
        const val HORIZONTAL = 1
        const val GRID = 2
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildAdapterPosition(view) //각 아이템뷰의 순서 (index)
        val totalItemCount = state.itemCount                //총 아이템 수
        //val scrollPosition = state.targetScrollPosition     //스크롤 됐을때 아이템 position


        when(oriental){
            VERTICAL -> setVertical(position, outRect, rightSpace, totalItemCount)
            HORIZONTAL -> setHorizontal(position, outRect, rightSpace, bottomSpace, totalItemCount)
            GRID -> setGrid(position, outRect, spanCount, rightSpace, bottomSpace)
        }
    }

    private fun setGrid(position: Int, outRect: Rect, spanCount: Int?, rightSpace: Int, bottomSpace: Int) {
        spanCount?.let {
            val column = position % spanCount + 1      // 1부터 시작
            val row = ((position - 1) / spanCount) + 1

            outRect.run {
                if (row != spanCount) {
                    bottom = bottomSpace.dp
                    if (column != spanCount) right = rightSpace.dp
                }
            }
        }
    }

    private fun setVertical(position: Int, outRect: Rect, rightSpace: Int, totalItemCount: Int) {}

    private fun setHorizontal(position: Int, outRect: Rect, rightSpace: Int, bottomSpace: Int, totalItemCount: Int) {
        if (position != 0) outRect.right = rightSpace.dp
        if (position != totalItemCount-1) outRect.right = rightSpace.dp
    }
}