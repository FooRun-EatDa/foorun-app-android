package kr.foorun.uni_eat.feature.map

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.contains
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kr.foorun.model.tag.SearchTag
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentMapBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.search.SearchBottomSheetFragment
import kr.foorun.uni_eat.feature.map.bottom_sheet.fragment.shop.ShopBottomSheetFragment
import kr.foorun.uni_eat.feature.map.search.fragment.MapSearchFragment
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

@AndroidEntryPoint
class MapFragment
    : BaseFragment<FragmentMapBinding, MapViewModel>(FragmentMapBinding::inflate)
    , MapView.CurrentLocationEventListener
    , MapView.MapViewEventListener // doesn't work if using object way but implementation works (Listener is detached when fragment gone)
{
    override val fragmentViewModel: MapViewModel by viewModels()
    private val searchTagViewModel: SearchTagViewModel by viewModels()
    private var shopBottomSheetFragment : ShopBottomSheetFragment? = null
    private var searchBottomSheetFragment : SearchBottomSheetFragment? = null
    private var mapView : MapView? = null

    private val searchTagAdapter by lazy { SearchTagAdapter(searchTagViewModel.apply {
        repeatOnStarted { eventFlow.collect{ tagHandleEvent(it)} } }) }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        onBackPressedListener()
        binding {
            mainSearchCL.bringToFront() // to put the view above mapView
            searchTagRC.adapter = searchTagAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        if(mapView != null){
            if(!binding.mapFL.contains(mapView!!)) binding.mapFL.addView(mapView)
        } else {
            mapView = MapView(requireActivity()).apply {
                setCurrentLocationEventListener(this@MapFragment)
                setMapViewEventListener(this@MapFragment)}
            binding.mapFL.addView(mapView)
        }
    }

    override fun onStop() {
        super.onStop()
        binding.mapFL.removeView(mapView)
        mapView = null
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeAndInitViewModel() {
        binding.viewModel = fragmentViewModel.apply {

            searchTags.observe(this@MapFragment) {
                searchTagAdapter.submitList(it)
                searchTagAdapter.notifyDataSetChanged()
                if(it[0].isPicked) showShopBottom() //fixme test
                if(it[1].isPicked) { //fixme test
                    showSearchBottom()
                    setVisibleMainSearch(false)
                }
            }

            searchWord.observe(this@MapFragment){
                //todo pick the shop given from MapSearchFragment on the map with lng things
            }

            repeatOnStarted { eventFlow.collect { handleEvent(it) } }
        }
    }

    private fun showShopBottomSheet() {
        shopBottomSheetFragment = ShopBottomSheetFragment ( { onBackPressedListener() },{
            if(it == BottomSheetBehavior.STATE_EXPANDED) {
                navigateToFrag(MapFragmentDirections.actionMapFragmentToShopDetailFragment())
                shopBottomSheetFragment?.collapse()
            }
            if(it == BottomSheetBehavior.STATE_HIDDEN) dismissShopBottomSheet()
        }).show(requireActivity().supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun showSearchBottomSheet() {
        searchBottomSheetFragment = SearchBottomSheetFragment( { onBackPressedListener() } , {
            if(it == BottomSheetBehavior.STATE_HIDDEN) {
                dismissSearchBottomSheet()
                fragmentViewModel.setVisibleMainSearch(true)
            }
        }).show(requireActivity().supportFragmentManager, R.id.view_bottom_sheet)
    }

    private fun dismissShopBottomSheet() {
        shopBottomSheetFragment?.dismiss(requireActivity().supportFragmentManager)
        shopBottomSheetFragment = null
    }

    private fun dismissSearchBottomSheet() {
        searchBottomSheetFragment?.dismiss(requireActivity().supportFragmentManager)
        searchBottomSheetFragment = null
    }

    private fun onBackPressedListener() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    if (shopBottomSheetFragment != null && shopBottomSheetFragment!!.handleBackKeyEvent())
                        shopBottomSheetFragment?.hide()

                    else if (searchBottomSheetFragment != null && searchBottomSheetFragment!!.handleBackKeyEvent())
                        searchBottomSheetFragment?.hide()

                    else findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun handleEvent(event: MapViewModel.MapEvent) = when (event) {
        is MapViewModel.MapEvent.ShowShop -> showShopBottomSheet()
        is MapViewModel.MapEvent.ShowSearch -> showSearchBottomSheet()
        is MapViewModel.MapEvent.NavigateToSearch -> MapSearchFragment{
            fragmentViewModel.setWord(it)
        }.show(requireActivity().supportFragmentManager,"")
        is MapViewModel.MapEvent.LocateMap -> checkLocationService { locationPermission() }
    }

    private fun tagHandleEvent(event: SearchTagViewModel.TagEvent) = when (event) {
        is SearchTagViewModel.TagEvent.TagClick -> {
            val tag = event.searchTag
            val arr = ArrayList<SearchTag>()
            fragmentViewModel.searchTags.value?.map { arr.add(it) }
            for( i in arr.indices ) if(arr[i].tagName == tag.tagName) arr[i] = SearchTag(tag.tagName,!tag.isPicked)
            fragmentViewModel.setTags(arr)
        }
    }

    private fun locationPermission() = askPermission(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        deniedMessage = getString(R.string.location_permission_deniedMessage),
        onGranted = { locateMe() }, onDenied = {  })

    private fun locateMe() { mapView?.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading }
    private fun locatePoint(lat : Double, lng: Double) {
        mapView?.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat, lng), true)
        //todo make marker at point
    }

    override fun onMapViewInitialized(p0: MapView?) {}
    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {}
    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {
        shopBottomSheetFragment?.hide()
        searchBottomSheetFragment?.hide()
    }
    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {}
    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {}
    override fun onCurrentLocationUpdate(map: MapView?, p1: MapPoint?, p2: Float) {
        map?.setMapCenterPoint(p1,true)
        if(map?.currentLocationTrackingMode != MapView.CurrentLocationTrackingMode.TrackingModeOff)
            map?.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff
    }
    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {}
    override fun onCurrentLocationUpdateFailed(p0: MapView?) {}
    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {}

}
