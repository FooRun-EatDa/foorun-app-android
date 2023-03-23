package kr.foorun.uni_eat.feature.article.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.databinding.FragmentArticleSearchBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

@AndroidEntryPoint
class ArticleSearchFragment: BaseFragment<FragmentArticleSearchBinding, ArticleSearchViewModel>(FragmentArticleSearchBinding::inflate){
    override val fragmentViewModel: ArticleSearchViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            searchWord.observe(this@ArticleSearchFragment){}
            repeatOnStarted { viewEvent.collect{ popUpBackStack() } }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
    }

}