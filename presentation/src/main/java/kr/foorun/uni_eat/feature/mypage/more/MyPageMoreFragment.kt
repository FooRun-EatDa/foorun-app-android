package kr.foorun.uni_eat.feature.mypage.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentMyPageMoreBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment

class MyPageMoreFragment :BaseFragment<FragmentMyPageMoreBinding,MyPageMoreViewModel>(FragmentMyPageMoreBinding::inflate){
    override val fragmentViewModel: MyPageMoreViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

}