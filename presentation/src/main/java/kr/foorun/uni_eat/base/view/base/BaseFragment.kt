package kr.foorun.uni_eat.base.view.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.gun0912.tedpermission.rx3.TedPermission
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
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

    //    abstract fun onViewEvent(event : Any)
    abstract fun observeAndInitViewModel()
    //    abstract fun updateLocale()
    abstract fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)

    protected fun binding(action: T.() -> Unit) { binding.run(action) }

    // navigateToAct(act::class) { putExtra(..) }
    protected inline fun navigateToAct(
        act: Class<*>,
        crossinline body: Intent.() -> Unit
    ) = Intent(requireContext(),act).let {
        body(it)
        startActivity(it)
    }
    protected fun navigateToAct(act : Class<*>) = startActivity(Intent(requireContext(),act))
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

    fun askPermission(vararg permissions : String, deniedMessage: String, onGranted: () -> Unit, onDenied: () -> Unit) {
        val t = TedPermission.create()
            .setDeniedMessage(deniedMessage)
            .setPermissions(*permissions)

        t.request().subscribe { result ->
            when (result.isGranted) {
                true -> onGranted()
                else -> onDenied()
            }
        }
    }

    fun log(str: String) = Log.e("popo",str) //for test
}