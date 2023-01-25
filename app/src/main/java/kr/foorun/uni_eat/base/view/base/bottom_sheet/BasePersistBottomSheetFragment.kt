package kr.foorun.uni_eat.base.view.base.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.foorun.uni_eat.databinding.FragmentPersistBottomSheetBinding

abstract class BasePersistBottomSheetFragment<CollapseBinding : ViewDataBinding, ExpandBinding : ViewDataBinding>(
    @LayoutRes private val collapseResId: Int,
    @LayoutRes private val expandResId: Int,
    private val heightType: HeightType = HeightType.MATCH,
) : Fragment() {

    private var _binding: FragmentPersistBottomSheetBinding? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected val binding: FragmentPersistBottomSheetBinding
        get() = _binding ?: throw IllegalAccessException("Can not access destroyed view")

    @Suppress("MemberVisibilityCanBePrivate")
    protected val bottomSheetBehavior by lazy { BottomSheetBehavior.from(binding.flContainer) }

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var collapseBinding: CollapseBinding

    @Suppress("MemberVisibilityCanBePrivate")
    protected lateinit var expandBinding: ExpandBinding

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPersistBottomSheetBinding.inflate(inflater, container, false).apply {
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

        return _binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomSheetBehavior()
        collapseBinding.init()
        expandBinding.init()
    }

    private fun ViewDataBinding.init() {
        lifecycleOwner = this@BasePersistBottomSheetFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
