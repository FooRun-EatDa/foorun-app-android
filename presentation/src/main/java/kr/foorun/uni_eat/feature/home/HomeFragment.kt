package kr.foorun.uni_eat.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentHomeBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(FragmentHomeBinding::inflate) {
    override val fragmentViewModel: HomeViewModel by viewModels()

    override fun observeAndInitViewModel() = binding {
        viewModel = fragmentViewModel.apply {

        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {

    }

}