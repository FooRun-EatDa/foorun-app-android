package kr.foorun.uni_eat.base.view.base.context_view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.rx3.TedPermission
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.view.base.loading.LoadingFragment
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.feature.main.MainActivity
import java.util.*

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment <T : ViewDataBinding, V : BaseViewModel>(private val inflate: Inflate<T>)
    : Fragment()  {

    private var _binding: T? = null
    val binding get() = _binding!!

    protected abstract val fragmentViewModel: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        binding.lifecycleOwner = this
        afterBinding(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { observeAndInitViewModel() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * please, type here about initiating viewModel or observer that is related to viewModel only
     */
    abstract fun observeAndInitViewModel()
    //    abstract fun updateLocale()

    /**
     * please, type here about initiating view only
     */
    abstract fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)

    protected fun binding(action: T.() -> Unit) { binding.run(action) }

    /**
     * ex) navigateToAct(act::class) { putExtra(..) }
     */
    protected inline fun navigateToAct(
        act: Class<*>,
        crossinline body: Intent.() -> Unit
    ) = Intent(requireContext(),act).let {
        body(it)
        startActivity(it)
    }
    protected fun navigateToAct(act : Class<*>) = startActivity(Intent(requireContext(),act))

    /**
     * to execute action of navigation component
     */
    protected fun navigateToFrag(act : NavDirections) = findNavController().navigate(act)

    @SuppressLint("ResourceType")
    protected fun fetchFrag(layoutResId: Int, fragment: Fragment){
        childFragmentManager.run{
            if(findFragmentById(layoutResId) == null)
                beginTransaction()
                    .add(layoutResId,fragment)
                    .commit()
            else
                beginTransaction()
                    .replace(layoutResId, fragment)
                    .commit()

            executePendingTransactions()
        }
    }

    protected fun toast(string: String) = Toast.makeText(requireContext(),string,Toast.LENGTH_SHORT).show()

    protected fun Fragment.hideKeyboard() = requireActivity().currentFocus?.also { it.hideKeyboard() }

    private fun View.hideKeyboard() =
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
            it.hideSoftInputFromWindow(windowToken, 0) }

    fun checkLocationService(action : () -> Unit) {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) action()
        else toast(getString(R.string.turnOn_GPS))
    }

    @SuppressLint("CheckResult")
    fun askPermission(vararg permissions: String, deniedMessage: String?, onGranted: () -> Unit, onDenied: () -> Unit) {
        val t = if (deniedMessage.isNullOrBlank())
            TedPermission.create()
                .setDeniedMessage(deniedMessage)
                .setPermissions(*permissions)
        else
            TedPermission.create()
                .setPermissions(*permissions)

        t.request().subscribe { result ->
            when (result.isGranted) {
                true -> onGranted()
                else -> onDenied()
            }
        }
    }

    protected fun imagePermission(onGranted: () -> Unit, onDenied: () -> Unit) {
        val version = Build.VERSION.SDK_INT
        val permissions = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
        else if (version in Build.VERSION_CODES.Q + 1..Build.VERSION_CODES.S_V2) permissions.add(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        else permissions.addAll(listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ))

        askPermission(
            *permissions.toTypedArray(),
            deniedMessage = getString(R.string.gallery_permission),
            onGranted = { onGranted() },
            onDenied = { onDenied() }
        )
    }

    protected fun startGallery(isMultiple: Boolean = false) = Intent().apply {
        if (isMultiple){
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            action = Intent.ACTION_GET_CONTENT
        } else {
            action = Intent.ACTION_PICK
            setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*") }
        }

    protected fun onBackPressedListener(action: () -> Unit) {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() { action() }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    protected fun handleBaseViewEvent(event: BaseViewModel.BaseEvent) = when(event){
        is BaseViewModel.BaseEvent.Back -> popUpBackStack()
    }

    protected fun popUpBackStack() = findNavController().popBackStack()

    protected fun isVisibleBottomNav(visible: Boolean) = requireActivity().run {
        if(this is MainActivity) this.bottomVisible(visible)
    }

    protected fun getColor(color: Int) = ContextCompat.getColor(requireActivity(),color)

    fun log(str: String) = Log.e("popo",str) //for test

    fun showLoading() = LoadingFragment().show(requireActivity().supportFragmentManager,"")
}