package kr.foorun.uni_eat.base.view.base.context_view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gun0912.tedpermission.rx3.TedPermission
import kr.foorun.presentation.R
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import java.util.*

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel>(
    val bindingFactory: (LayoutInflater) -> T
) : AppCompatActivity() {

    protected lateinit var binding: T
        private set

    protected abstract val activityViewModel: V

    protected val tag: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        binding.lifecycleOwner = this
        afterBinding()
        observeAndInitViewModel()
    }

    abstract fun observeAndInitViewModel()
//    abstract fun updateLocale()
    abstract fun afterBinding()

    protected fun binding(action: T.() -> Unit) {
        binding.run(action)
    }

    // navigateToAct(act::class) { putExtra(..) }
    inline fun navigateToAct(
        act: Class<*>,
        crossinline body: Intent.() -> Unit
    ) = Intent(this,act).let {
        body(it)
        startActivity(it)
    }
    fun navigateToAct(act : Class<*>) = startActivity(Intent(this,act))

    fun getCameraIntent(): Intent = Intent.createChooser(
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            .apply { type = "image/*"}, "Select File")

    fun startGallery() = Intent().apply {
        action = Intent.ACTION_PICK
        setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*") }

    fun getLocal() : String{
        val locale =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) resources.configuration.locales[0]
            else resources.configuration.locale
        return locale.language
    }

    @SuppressLint("ResourceType", "CommitTransaction")
    fun fetchFrag(layoutResId: Int, fragment: Fragment){
        supportFragmentManager.run{
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

    fun toast(str:String) = Toast.makeText(this,str, Toast.LENGTH_LONG).show()

    fun Activity.hideKeyboard() = currentFocus?.also { it.hideKeyboard() }

    private fun View.hideKeyboard() =
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.also {
            it.hideSoftInputFromWindow(windowToken, 0) }

    fun checkLocationService(action : () -> Unit) {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) action()
        else toast(getString(R.string.turnOn_GPS))
    }

    @SuppressLint("CheckResult")
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