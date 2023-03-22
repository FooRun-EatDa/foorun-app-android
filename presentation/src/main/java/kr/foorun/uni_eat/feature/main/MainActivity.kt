package kr.foorun.uni_eat.feature.main

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.ActivityMainBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseActivity

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>({ActivityMainBinding.inflate(it)}) {

    override val activityViewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun afterBinding() = binding {
        setUpBottomNavigationView()
    }

    override fun observeAndInitViewModel() = binding {
        viewModel = activityViewModel.apply {
        }
    }

    private fun setUpBottomNavigationView() = binding {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        setDestinationListener()
    }

    private fun setDestinationListener() = navController.addOnDestinationChangedListener { controller, destination, arg ->
        if(arg != null){
            if (arg.isEmpty) bottomVisible(true)
            else if(arg.getBoolean(getString(R.string.hide_bottom))) bottomVisible(false)
        } else bottomVisible(true)
    }

    fun bottomVisible(isVisible: Boolean) = activityViewModel.setBottomVisible(isVisible)
}
