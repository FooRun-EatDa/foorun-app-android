package kr.foorun.uni_eat.feature.map

import android.util.Log
import androidx.activity.viewModels
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.BaseActivity
import kr.foorun.uni_eat.databinding.ActivityMapBinding
import kr.foorun.uni_eat.feature.map.dialog.ShopBottomSheetFragment

class MapActivity : BaseActivity<ActivityMapBinding,MapViewModel>({ActivityMapBinding.inflate(it)}){
    override val activityViewModel: MapViewModel by viewModels()
    private lateinit var shopBottomSheetFragment : ShopBottomSheetFragment

    override fun afterBinding() {}

    override fun observeAndInitViewModel() {
        binding {
            viewModel = activityViewModel.apply {
                shopBottomSheetFragment = ShopBottomSheetFragment()
                shopBottomSheetFragment.show(supportFragmentManager, R.id.view_bottom_sheet)
            }
        }
    }

    override fun onBackPressed() {
        if (shopBottomSheetFragment.handleBackKeyEvent()) {
            // no-op
        } else {
            super.onBackPressed()
        }

    }

}
