package kr.foorun.uni_eat.base


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
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
//        updateLocale()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeAndInitViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun observeAndInitViewModel()
//    abstract fun updateLocale()

    fun changeLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        fragmentViewModel.changeResourcesLocale()
    }


    protected fun binding(action: T.() -> Unit) {
        binding.run(action)
    }

    // navigateToAct(act::class) { putExtra(..) }
    inline fun navigateToAct(
        act: Class<*>,
        crossinline body: Intent.() -> Unit
    ) = Intent(requireContext(),act).let {
        body(it)
        startActivity(it)
    }
    fun navigateToAct(act : Class<*>) = startActivity(Intent(requireContext(),act))
    fun navigateToFrag(act : NavDirections) = findNavController().navigate(act)

    @SuppressLint("ResourceType")
    fun fetchFrag(layoutResId: Int, fragment: Fragment){
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

    fun toast(string: String)
            = Toast.makeText(requireContext(),string,Toast.LENGTH_SHORT).show()

    fun Fragment.hideKeyboard() = requireActivity().currentFocus?.also { it.hideKeyboard() }

    private fun View.hideKeyboard() =
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
            it.hideSoftInputFromWindow(windowToken, 0)
        }
}