package kr.foorun.uni_eat.feature.mypage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentMyPageBinding
import kr.foorun.uni_eat.base.view.base.base_layout.BaseTab
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.view.base.recycler.decorator.TagDecorator
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.article.entire.adapter.ArticleDetailViewPagerAdapter
import kr.foorun.uni_eat.feature.map.SearchTagAdapter
import kr.foorun.uni_eat.feature.map.TagViewHolder.Companion.INTRODUCE

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding, MyPageViewModel>(FragmentMyPageBinding::inflate) {
    override val fragmentViewModel: MyPageViewModel by viewModels()
    private val tagAdapter: SearchTagAdapter by lazy { SearchTagAdapter(type = INTRODUCE) }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

            user.observe(this@MyPageFragment){
                tagAdapter.submitList(it?.tags)
                tagAdapter.notifyDataSetChanged()
                binding.user = it
            }

            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding{
        pageTab.run {
            pager.adapter = ArticleDetailViewPagerAdapter(requireActivity().supportFragmentManager,lifecycle)
            setTabItem(listOf(BaseTab.CustomTabItem(getString(R.string.article)), BaseTab.CustomTabItem(getString(R.string.bookMark))))
        }

        tagRecycler.run {
            adapter = tagAdapter
            addItemDecoration(TagDecorator())
        }
    }

    private fun handleEvent(event: MyPageViewModel.MyPageEvent) = when(event) {
        is MyPageViewModel.MyPageEvent.SchoolCertification -> {}
        is MyPageViewModel.MyPageEvent.MyPageMore -> navigateToFrag(MyPageFragmentDirections.actionMyPageToMyPageMoreFragment())
        is MyPageViewModel.MyPageEvent.WriteArticle -> {}
    }

}