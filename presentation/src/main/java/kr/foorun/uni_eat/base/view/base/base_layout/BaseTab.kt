package kr.foorun.uni_eat.base.view.base.base_layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.ItemTabItemBinding

class BaseTab: ConstraintLayout {

    lateinit var tab: TabLayout
    lateinit var pager: ViewPager2

    constructor(context: Context) : super(context) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        val infService = Context.LAYOUT_INFLATER_SERVICE
        val li = context.getSystemService(infService) as LayoutInflater
        val v = li.inflate(R.layout.base_tab, this, false)
        addView(v)

        tab = findViewById(R.id.tab)
        pager = findViewById(R.id.pager)
    }

    fun setTabItem(items: List<CustomTabItem>) {
        TabLayoutMediator(tab, pager) { tab, position ->
            DataBindingUtil.bind<ItemTabItemBinding>(LayoutInflater.from(context).inflate(R.layout.item_tab_item , null)
            )?.apply {
                val p = items[position]
                title = p.title
                number = p.number
            }?.run { tab.customView = this@run.root }
        }.attach()
    }

    data class CustomTabItem(
        val title: String,
        val number: String = ""
    )
}