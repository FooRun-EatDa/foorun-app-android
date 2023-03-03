package kr.foorun.uni_eat.feature.main

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.ActivityMainBinding
import kr.foorun.uni_eat.base.view.base.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>({ActivityMainBinding.inflate(it)}) {

    override val activityViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun afterBinding() = binding {
        setUpBottomNavigationView()
    }

    private fun setUpBottomNavigationView() = binding {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        setDestinationListener()
    }

    private fun setDestinationListener() = navController.addOnDestinationChangedListener { controller, destination, arg ->
        if(arg != null){
            if (arg.isEmpty) binding.bottomNav.visibility = View.VISIBLE
            else if(arg.getBoolean(getString(R.string.hide_bottom))) binding.bottomNav.visibility = View.GONE
        } else binding.bottomNav.visibility = View.VISIBLE
    }

    override fun observeAndInitViewModel() = binding {
        viewModel = activityViewModel.apply {
        }
    }

}
