package kr.foorun.uni_eat.feature.mypage.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentMyPageEmailBinding
import kr.foorun.uni_eat.base.view.base.base_layout.BaseTextView
import kr.foorun.uni_eat.base.view.base.context_view.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class MyPageEmailFragment: BaseFragment<FragmentMyPageEmailBinding,MyPageEmailViewModel>(FragmentMyPageEmailBinding::inflate){
    override val fragmentViewModel: MyPageEmailViewModel by viewModels()

    override fun observeAndInitViewModel() = binding{
        viewModel = fragmentViewModel.apply {

            emailCheck.observe(this@MyPageEmailFragment){
                emailTextHandle(it)
            }

            repeatOnStarted { viewEvent.collect{ handleBaseViewEvent(it)} }
        }
    }

    private fun emailTextHandle(event: MyPageEmailViewModel.EmailCase) {
        when (event){
            is MyPageEmailViewModel.EmailCase.Nothing -> binding.emailAlert.setTextColor("",R.color.black)
            is MyPageEmailViewModel.EmailCase.Success -> navigateToFrag(MyPageEmailFragmentDirections.actionMyPageEmailFragmentToEmailDoneFragment(""))
            is MyPageEmailViewModel.EmailCase.WrongEmail -> binding.emailAlert.setTextColor(getString(R.string.no_email),R.color.red)
        }
    }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding {
    }
}