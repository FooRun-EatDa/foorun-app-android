package kr.foorun.uni_eat.base.view.base.recycler.decorator.grid

import android.graphics.Rect
import android.view.View
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.uni_eat.base.view.base.dp

class GridSpaceItemDecoration(private val spanCount: Int, private val space: Int): RecyclerView.ItemDecoration() {

    private val dp = space.dp
    private val sideDp = (space + 10).dp

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount + 1      // 1부터 시작

        /** 마지막 열(column-N)에 있는 아이템인 경우 우측에 [space] 만큼의 여백을 추가한다 */
        if (column == spanCount){
            outRect.right = sideDp
        }

        if (column == 1){
            outRect.left = sideDp
            outRect.right = dp
        }

        outRect.run {
            bottom = dp
        }
    }
}