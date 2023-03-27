package kr.foorun.uni_eat.feature.mypage.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentEditMyPageBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment

class EditMyPageFragment: BaseFragment<FragmentEditMyPageBinding, EditMyPageViewModel>(FragmentEditMyPageBinding::inflate){

    override val fragmentViewModel: EditMyPageViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

}