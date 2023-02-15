package kr.foorun.uni_eat.feature.map

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.data.tag.SearchTag
import kr.foorun.uni_eat.R
import kr.foorun.uni_eat.base.view.base.BaseActivity
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.databinding.ActivityMapBinding
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search.SearchBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop.ShopBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.shop_detail.ShopDetailActivity
import kr.foorun.uni_eat.feature.map.fragment.search.MapSearchFragment
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener

@AndroidEntryPoint
class MapActivity
    : BaseActivity<ActivityMapBinding, MapViewModel>({ActivityMapBinding.inflate(it)})
    , MapViewEventListener // doesn't work if using object way but implementation works (Listener is detached when fragment gone)
{
    override val activityViewModel: MapViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private var shopBottomSheetFragment : ShopBottomSheetFragment? = null
    private var searchBottomSheetFragment : SearchBottomSheetFragment? = null
    private val mapView by lazy { MapView(this).apply { setMapViewEventListener(this@MapActivity) } }
    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tgaHandleEvent(it)} } }) }

    override fun afterBinding() {
        binding {
            mapFL.addView(mapView)
            mainSearchCL.bringToFront() // to put the view above mapView
            searchTagRC.adapter = searchTagAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        binding.viewModel = activityViewModel.apply {

            searchTags.observe(this@MapActivity) {
                searchTagAdapter.submitList(it)
                searchTagAdapter.notifyDataSetChanged()
                if(it[0].isPicked) showShopBottom() //fixme test
                if(it[1].isPicked) { //fixme test
                    showSearchBottom()
                    setVisibleMainSearch(false)
                }
            }

            searchWord.observe(this@MapActivity){
                //todo pick the shop given from MapSearchFragment on the map with lng things
            }

            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    private fun showShopBottomSheet() {
        shopBottomSheetFragment = ShopBottomSheetFragment ( { onBackPressed() },{
            if(it == BottomSheetBehavior.STATE_EXPANDED)
                navigateToAct(ShopDetailActivity::class.java) {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                shopBottomSheetFragment?.collapse()
            }
            if(it == BottomSheetBehavior.STATE_HIDDEN) dismissShopBottomSheet()
        }).show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun showSearchBottomSheet() {
        searchBottomSheetFragment = SearchBottomSheetFragment( { onBackPressed() } , {
            if(it == BottomSheetBehavior.STATE_HIDDEN) {
                dismissSearchBottomSheet()
                activityViewModel.setVisibleMainSearch(true)
            }
        }).show(supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun dismissShopBottomSheet() {
        shopBottomSheetFragment?.dismiss(supportFragmentManager)
        shopBottomSheetFragment = null
    }

    private fun dismissSearchBottomSheet() {
        searchBottomSheetFragment?.dismiss(supportFragmentManager)
        searchBottomSheetFragment = null
    }

    override fun onBackPressed() {
        if (shopBottomSheetFragment != null && shopBottomSheetFragment!!.handleBackKeyEvent())
            shopBottomSheetFragment?.hide()

        else if (searchBottomSheetFragment != null && searchBottomSheetFragment!!.handleBackKeyEvent())
            searchBottomSheetFragment?.hide()

        else super.onBackPressed()
    }

    private fun handleEvent(event: MapViewModel.MapEvent) = when (event) {
        is MapViewModel.MapEvent.ShowShop -> showShopBottomSheet()
        is MapViewModel.MapEvent.ShowSearch -> showSearchBottomSheet()
        is MapViewModel.MapEvent.NavigateToSearch -> MapSearchFragment{
            activityViewModel.setWord(it)
        }.show(supportFragmentManager,"")
    }

    private fun tgaHandleEvent(event: SearchTagViewModel.TagEvent) = when (event) {
        is SearchTagViewModel.TagEvent.TagClick -> {
            val tag = event.searchTag
            val arr = ArrayList<SearchTag>()
            activityViewModel.searchTags.value?.map { arr.add(it) }
            for( i in arr.indices ) if(arr[i].tagName == tag.tagName) arr[i] = SearchTag(tag.tagName,!tag.isPicked)
            activityViewModel.setTags(arr)
        }
    }

    override fun onMapViewInitialized(p0: MapView?) {}
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        dismissShopBottomSheet()
        dismissSearchBottomSheet()
    }
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}

}
