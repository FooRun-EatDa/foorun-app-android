package kr.foorun.uni_eat.feature.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import kr.foorun.presentation.databinding.FragmentReportDialogBinding
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted

class ReportDialogFragment() : DialogFragment() {
    val fragmentViewModel: ReportViewModel by viewModels()
    private var _binding: FragmentReportDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = fragmentViewModel.apply {
                repeatOnStarted { eventFlow.collect { handleEvent(it) } }
            }
        }
    }

    private fun handleEvent(event: ReportViewModel.ReportEvent) = when (event) {
        else -> {}
    }
}