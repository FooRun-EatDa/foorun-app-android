package kr.foorun.uni_eat.feature.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentMyBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel

class MyFragment : BaseFragment<FragmentMyBinding,BaseViewModel>(FragmentMyBinding::inflate) {
    override val fragmentViewModel: BaseViewModel by viewModels()

    override fun observeAndInitViewModel() {
    }

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }
}