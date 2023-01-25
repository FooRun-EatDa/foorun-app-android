package kr.foorun.uni_eat.feature.map

import androidx.activity.viewModels
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.BaseActivity
import kr.foorun.uni_eat.databinding.ActivityMapBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.ShopBottomSheetFragment

class MapActivity : BaseActivity<ActivityMapBinding,MapViewModel>({ActivityMapBinding.inflate(it)}){
    override val activityViewModel: MapViewModel by viewModels()
    private lateinit var shopBottomSheetFragment : ShopBottomSheetFragment

    override fun afterBinding() {
        binding {
            showBottomSheet()

            test.setOnClickListener {
                showBottomSheet()
            }
        }
    }

    override fun observeAndInitViewModel() {
        binding {
            viewModel = activityViewModel.apply {

            }
        }
    }

    private fun showBottomSheet() {
        shopBottomSheetFragment =
            ShopBottomSheetFragment{ onBackPressed() }
                .show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    override fun onBackPressed() {
        if (shopBottomSheetFragment.handleBackKeyEvent()) {
            shopBottomSheetFragment.hide()
        } else {
            super.onBackPressed()
        }
    }

}
