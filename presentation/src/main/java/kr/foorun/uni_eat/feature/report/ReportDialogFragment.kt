package kr.foorun.uni_eat.feature.report


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentReportDialogBinding
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class ReportDialogFragment() : DialogFragment() {
    lateinit var binding: FragmentReportDialogBinding
    val fragmentViewModel: ReportDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
            }
        }
    }

    private fun handleEvent(event: ReportDialogViewModel.ReportEvent) = when (event) {
        is ReportDialogViewModel.ReportEvent.clickConfirm -> { Log.d("test","확인")}
        is ReportDialogViewModel.ReportEvent.clickCancel -> { dismiss() }
    }

    fun show(
        fragmentManager: FragmentManager,
        @IdRes containerViewId: Int,
    ): ReportDialogFragment =
        fragmentManager.findFragmentByTag(tag) as? ReportDialogFragment
            ?: ReportDialogFragment().apply {
                fragmentManager.beginTransaction()
                    .replace(containerViewId, this, tag)
                    .commitAllowingStateLoss()
            }
}