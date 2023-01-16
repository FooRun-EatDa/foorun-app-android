package kr.foorun.uni_eat.feature.main

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.uni_eat.base.BaseActivity
import kr.foorun.uni_eat.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>({ActivityMainBinding.inflate(it)}) {

    override val activityViewModel: MainViewModel by viewModels()

    override fun observeAndInitViewModel() {
        binding {
            viewModel = activityViewModel.apply {

            }
        }
    }
}
