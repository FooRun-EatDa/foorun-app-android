package kr.foorun.uni_eat.feature.article.entire.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.foorun.uni_eat.feature.article.entire.inner.ArticleEntireInnerFragment

class ArticleDetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val num = 2

    override fun getItemCount(): Int {
        return num
    }

    override fun createFragment(position: Int): Fragment {
        return ArticleEntireInnerFragment.newInstance(position)
    }
}