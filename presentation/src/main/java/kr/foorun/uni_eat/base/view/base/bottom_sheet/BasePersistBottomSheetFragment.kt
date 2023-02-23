package kr.foorun.uni_eat.base.view.base.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.presentation.databinding.FragmentSearchBottomSheetBinding
import kr.foorun.uni_eat.base.view.base.BaseFragment
import kr.foorun.uni_eat.base.viewmodel.BaseViewModel
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

abstract class BasePersistBottomSheetFragment<CollapseBinding : ViewDataBinding, ExpandBinding : ViewDataBinding>(
    @LayoutRes private val collapseResId: Int,
    @LayoutRes private val expandResId: Int,
    private val heightType: HeightType = HeightType.MATCH,
) : BaseFragment<FragmentSearchBottomSheetBinding, BaseViewModel>(FragmentSearchBottomSheetBinding::inflate) {

    @Suppress("MemberVisibilityCanBePrivate")
    protected val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var collapseBinding: CollapseBinding

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var expandBinding: ExpandBinding

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

    override fun afterBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
        binding{
            collapseBinding = DataBindingUtil.inflate(inflater,
                collapseResId,
                viewCollapseContainer,
                true)
            expandBinding = DataBindingUtil.inflate(inflater,
                expandResId,
                viewExpandContainer,
                true)

            viewContent.layoutParams.height = heightType.layoutParamHeight
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehavior()
        collapseBinding.init()
        expandBinding.init()

        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted { fragmentViewModel.viewEvent.collect{ handleEvent(it) } }
        }
    }

    private fun handleEvent(event: BaseViewModel.BaseEvent) = when (event) {
        is BaseViewModel.BaseEvent.Back -> bottomSheetBackClicked()
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@BasePersistBottomSheetFragment
    }

    private fun setupBottomSheetBehavior() = with(bottomSheetBehavior) {
        val bottomSheetCallback =
            BaseBottomSheetCallback(
                this,
                binding.root,
                binding.flContainer,
                binding.viewCollapseContainer,
                binding.viewExpandContainer,
            ){ onStateChanged(it) }
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
    fun hide() {
        if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    @Suppress("MemberVisibilityCanBePrivate")
    open fun handleBackKeyEvent() =
        when {
            !isAdded -> false
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

    fun backButtonVisible(show : Boolean){
        binding.backBTN.isVisible = show
    }
}
