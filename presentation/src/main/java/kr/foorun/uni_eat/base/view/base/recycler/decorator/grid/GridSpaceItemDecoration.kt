package kr.foorun.uni_eat.base.view.base.recycler.decorator.grid

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.foorun.uni_eat.base.view.base.dp

/**
 * please add values for int. it's gonna convert int-value you input into dp
 *
 * default value for articles :
 *
 * sideSpace == 17dp
 * gapSpace == 7dp
 * spanCount = 2
 * oriental = STAGGERED
 *
 * but anything can be created with parameter value and it's gonna be turned into dp value automatically in this code.
 */

class GridSpaceItemDecoration(gapSpace: Int = ARTICLE_GAP, sideSpace: Int = ARTICLE_SIDE_SPACE, private val spanCount: Int = ARTICLE_SPAN_COUNT): RecyclerView.ItemDecoration() {

    private val dp = gapSpace.dp
    private val sideDp = sideSpace.dp

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

    companion object{
        const val ARTICLE_SPAN_COUNT = 2
        const val ARTICLE_SIDE_SPACE = 17
        const val ARTICLE_GAP = 7
    }
}