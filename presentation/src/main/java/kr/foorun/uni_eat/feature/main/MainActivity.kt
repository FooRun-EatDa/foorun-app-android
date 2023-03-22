package kr.foorun.uni_eat.feature.main

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.ActivityMainBinding
import kr.foorun.uni_eat.base.view.base.context_view.BaseActivity
import kr.foorun.uni_eat.feature.home.HomeFragmentDirections


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>({ActivityMainBinding.inflate(it)}){

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
        setDestinationListener()
        setUpBottomNav()
    }

    /**
     * @setupWithNavController(navController) is to fetch view into frame when click bottom icon
     *
     * @setOnItemSelectedListener is to make sure action works properly,
     * if you use only setupWithNavController, view is not attached on frame when click bottom icon.
     */
    private fun setUpBottomNav() = binding {
        bottomNav.setupWithNavController(navController) //to fetch view into frame when click bottom icon

        bottomNav.setOnItemSelectedListener {//to make sure action works properly if you use
            when(it.itemId){
                R.id.home_nav -> true.apply { navController.setGraph(R.navigation.home_nav) }
                R.id.map_nav -> true.apply { navigate(HomeFragmentDirections.actionHomeFragmentToMapNav()) }
                R.id.event_nav -> true.apply { navigate(HomeFragmentDirections.actionHomeFragmentToEventNav()) }
                R.id.article_nav -> true.apply { navigate(HomeFragmentDirections.actionHomeFragmentToArticleNav()) }
                R.id.my_nav -> true.apply { navigate(HomeFragmentDirections.actionHomeFragmentToMyNav()) }
                else -> false
            }
        }
    }

    private fun navigate(directions: NavDirections) = navController.navigate(directions)

    private fun setDestinationListener() = navController.addOnDestinationChangedListener { controller, destination, arg ->
        if(arg != null){
            if (arg.isEmpty) bottomVisible(true)
            else if(arg.getBoolean(getString(R.string.hide_bottom))) bottomVisible(false)
        } else bottomVisible(true)
    }

    fun bottomVisible(isVisible: Boolean) = activityViewModel.setBottomVisible(isVisible)
}
