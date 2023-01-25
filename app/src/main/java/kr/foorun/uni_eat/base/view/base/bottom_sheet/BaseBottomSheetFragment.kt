package kr.foorun.uni_eat.base.view.base.bottom_sheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.data.const.Constant.Companion.BACK
import kr.foorun.uni_eat.base.BaseFragment
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
import kr.foorun.uni_eat.databinding.FragmentBottomSheetHideableBinding


abstract class BaseBottomSheetFragment <CollapseBinding : ViewDataBinding>(
    @LayoutRes private val collapseResId: Int,
    private val heightType: HeightType = HeightType.MATCH,
) : BaseFragment<FragmentBottomSheetHideableBinding, BaseViewModel>(FragmentBottomSheetHideableBinding::inflate) {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var collapseBinding: CollapseBinding

    private lateinit var bottomCallback : BaseBottomSheetCallback

    public override val fragmentViewModel: BaseViewModel by viewModels()

    abstract fun onStateChanged(state : Int)

    @Suppress("MemberVisibilityCanBePrivate")
    val isExpanded: Boolean
        get() = when (bottomSheetBehavior.state) {
            BottomSheetBehavior.STATE_EXPANDED,
            BottomSheetBehavior.STATE_HALF_EXPANDED,
            BottomSheetBehavior.STATE_SETTLING,
            -> true
            else -> false
        }

    override fun afterBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {
        binding {
            collapseBinding = DataBindingUtil.inflate(inflater,
                collapseResId,
                viewContainer,
                true)

            viewContent.layoutParams.height = heightType.layoutParamHeight
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehavior()
        collapseBinding.init()

        binding.viewModel = fragmentViewModel.apply {
            this.onViewEvent { if(it == BACK) { bottomSheetBackClicked() } }
        }
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@BaseBottomSheetFragment
    }

    private fun setupBottomSheetBehavior() = with(bottomSheetBehavior) {
        bottomCallback =
            BaseBottomSheetCallback(
                this,
                binding.root,
                binding.flContainer,
                binding.viewContainer,
                null
            ){ onStateChanged(it) }

        addBottomSheetCallback(bottomCallback)

        collapseBinding.root.doOnLayout {
            bottomSheetBehavior.peekHeight = it.height
        }
    }

    private fun setPeekHeight(isZero : Boolean){
        if(isZero) bottomSheetBehavior.peekHeight = 0
        else collapseBinding.root.doOnLayout {
            bottomSheetBehavior.peekHeight = it.height
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun expand() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun collapse() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun hide() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun handleBackKeyEvent() =
        when {
            isAdded -> true
            childFragmentManager.backStackEntryCount > 0 -> {
                childFragmentManager.popBackStackImmediate()
                true
            }
            isExpanded -> {
                collapse()
                true
            }
            else -> false
        }

    abstract fun bottomSheetBackClicked()

    fun changeHeightType(heightType: HeightType) {
        binding.viewContent.layoutParams.height = heightType.layoutParamHeight
    }

    enum class HeightType(val layoutParamHeight: Int) {
        WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
        MATCH(ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
