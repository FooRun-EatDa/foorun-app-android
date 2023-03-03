package kr.foorun.uni_eat.feature.article.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentArticleDetailBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.nonNullObserver


class ArticleDetailFragment : BaseFragment<FragmentArticleDetailBinding, ArticleDetailViewModel>(FragmentArticleDetailBinding::inflate) {
    override val fragmentViewModel: ArticleDetailViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {
            article.observe(this@ArticleDetailFragment){
                binding.article = it
            }
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
    }
}