package kr.foorun.uni_eat.feature.report


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import kr.foorun.presentation.R
import kr.foorun.presentation.databinding.FragmentReportDialogBinding
import kr.foorun.uni_eat.base.viewmodel.repeatOnStarted


class ReportDialogFragment : DialogFragment() {
    lateinit var binding: FragmentReportDialogBinding
    val fragmentViewModel: ReportDialogViewModel by viewModels()
    lateinit var alertDialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.fragment_report_dialog, null, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = fragmentViewModel.apply {
            repeatOnStarted {
                eventFlow.collect { handleEvent(it) }
            }
        }
        alertDialog = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()

        alertDialog.setCanceledOnTouchOutside(false)

        return alertDialog
    }

    private fun handleEvent(event: ReportDialogViewModel.ReportEvent) = when (event) {
        is ReportDialogViewModel.ReportEvent.clickConfirm -> {
            val reportContent: String = binding.reportDialogEDT.text.toString()

            if (reportContent.length == 0) {
                Toast.makeText(requireContext(), R.string.report_toast, Toast.LENGTH_SHORT).show()
            } else {
                binding.reportCompleteLL.visibility = View.VISIBLE
                binding.reportMainLL.visibility = View.GONE

                Log.d("Tgyuu", reportContent) // 신고 내용
                alertDialog.setCanceledOnTouchOutside(true)
                fragmentViewModel.reportDone()
            }
        }
        is ReportDialogViewModel.ReportEvent.reportDone -> {
            this?.dismiss()
        }
        is ReportDialogViewModel.ReportEvent.clickMainCancel -> {
            binding.reportCancelLL.visibility = View.VISIBLE
            binding.reportMainLL.visibility = View.GONE
        }
        is ReportDialogViewModel.ReportEvent.clickRealCancel -> {
            dismiss()
        }
        is ReportDialogViewModel.ReportEvent.returnMain -> {
            binding.reportMainLL.visibility = View.VISIBLE
            binding.reportCancelLL.visibility = View.GONE
        }
    }
}