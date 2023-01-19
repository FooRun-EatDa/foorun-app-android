package kr.foorun.uni_eat.base.view.base.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.base.BaseFragment
import kr.foorun.uni_eat.base.mvvm.BaseViewModel
import kr.foorun.uni_eat.databinding.FragmentBottomSheetHideableBinding

abstract class BottomSheetFragment <CollapseBinding : ViewDataBinding, V : BaseViewModel>(
    @LayoutRes private val collapseResId: Int,
    private val heightType: HeightType = HeightType.MATCH, // you should check out to adapt your view-size
) : BaseFragment<FragmentBottomSheetHideableBinding, V>(FragmentBottomSheetHideableBinding::inflate) {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var collapseBinding: CollapseBinding

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
        binding.apply {
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
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@BottomSheetFragment
    }

    private fun setupBottomSheetBehavior() = with(bottomSheetBehavior) {
        val bottomSheetCallback =
            BottomSheetCallback(
                this,
                binding.root,
                binding.flContainer,
                binding.viewContainer,
                null
            )
        addBottomSheetCallback(bottomSheetCallback)

        collapseBinding.root.doOnLayout {
            peekHeight = it.height
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
    open fun handleBackKeyEvent() =
        when {
            !isAdded -> false
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

    fun changeHeightType(heightType: HeightType) {
        binding.viewContent.layoutParams.height = heightType.layoutParamHeight
    }

    enum class HeightType(val layoutParamHeight: Int) {
        WRAP(ViewGroup.LayoutParams.WRAP_CONTENT),
        MATCH(ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
